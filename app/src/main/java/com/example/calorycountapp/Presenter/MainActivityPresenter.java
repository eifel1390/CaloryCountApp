package com.example.calorycountapp.Presenter;

public class MainActivityPresenter extends PresenterBase {

    private MainActivity mainActivity;
    private Context context;


    public MainActivityPresenter(MvpView mainActivity) {
        this.mainActivity = (MainActivity) mainActivity;
        context = this.mainActivity;
    }

    @Override
    public void viewIsReady(String ident) {
        //в момент подключения презентера идет команда на очистку временных баз
        //в полночь
        //setScheduledExecutorService();
    }

    @Override
    public void displayAnotherScreen(String nameOfScreen, String entityIdent) {
        Intent i = new Intent(mainActivity, SettingsActivity.class);
        mainActivity.startActivity(i);
    }

    //TODO тестовый метод,удалить
    public void testMethod(){
        /**получаем значение количество набранных калорий за день*/
        int caloryOfDay = NumberCaloryPreferences.getStoredCalory(context);
        /**получаем общее количество потребленных калорий (нужно,чтобы высчитывать среднее потребление)*/
        int constantCalory = NumberCaloryPreferences.getConstantCalory(context);
        /**если количество набранных калорий за день отрицательное,обнуляем*/
        if(caloryOfDay<0){
            caloryOfDay = 0;
        }
        /**получаем текущую дату,она будет потом отображаться под каждым стобцом графика*/
        String timeStamp = new SimpleDateFormat("m",new Locale("ru")).format(Calendar.getInstance().getTime());
        /**запускаем таск*/
        SetDataToHistoryDatabase taskHistory = new SetDataToHistoryDatabase(timeStamp,caloryOfDay,context);
        taskHistory.execute();
        /**добавляем к общему потребленному количеству калорий потребленное за день*/
        constantCalory += caloryOfDay;
        /**записываем общее потребленное количество калорий в преф*/
        NumberCaloryPreferences.setConstantCalory(context,constantCalory);
        /**обнуление данных в главном фрагменте потреблено за день */
        NumberCaloryPreferences.setStoredCalory(context,0);
        /**нужно,чтобы во фрагменте "Сегодня" данные обновились.То есть поскольку мы очистили
         временную базу то во фрагменте "Сегодня" не должно быть ничего*/
        mainActivity.recreateFragment();

    }

    //метод,который будет срабатывать ровно в полночь
    /*private void setScheduledExecutorService(){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int delayInHour = hour < 23 ? 23 - hour : 24 - (hour - 23);

        scheduler.scheduleAtFixedRate
                (new Runnable() {
                    public void run() {
                        //получаем значение количество набранных калорий за день
                        int caloryOfDay = NumberCaloryPreferences.getStoredCalory(context);
                        //получаем общее количество потребленных калорий (нужно,чтобы высчитывать среднее потребление)
                        int constantCalory = NumberCaloryPreferences.getConstantCalory(context);
                        //если количество набранных калорий за день отрицательное,обнуляем
                        if(caloryOfDay<0){
                            caloryOfDay = 0;
                        }
                        //получаем текущую дату,она будет потом отображаться под каждым стобцом графика
                        String timeStamp = new SimpleDateFormat("d MMM",new Locale("ru")).format(Calendar.getInstance().getTime());
                        //запускаем таск
                        SetDataToHistoryDatabase taskHistory = new SetDataToHistoryDatabase(timeStamp,caloryOfDay,context);
                        taskHistory.execute();
                        //добавляем к общему потребленному количеству калорий потребленное за день
                        constantCalory += caloryOfDay;
                        //записываем общее потребленное количество калорий в преф
                        NumberCaloryPreferences.setConstantCalory(context,constantCalory);
                        //в преф "потребленное за день" пишем 0
                        NumberCaloryPreferences.setStoredCalory(context,0);
                        //нужно,чтобы во фрагменте "Сегодня" данные обновились.То есть поскольку мы очистили
                        //временную базу то во фрагменте "Сегодня" не должно быть ничего
                        mainActivity.recreateFragment();
                    }
                }, delayInHour, 24, TimeUnit.HOURS);
    }*/

    /**таск,его задача записывать количество калорий набранных за день и текущую дату в базу данных "История"
     а также очищает временную базу (ту,которая наполняет фрагмент "Сегодня (DailyStatistics)")*/
    private class SetDataToHistoryDatabase extends AsyncTask<Void,Void,Void> {

        private int caloryNumberPerDay;
        private String currentDate;
        private DB db;

        public SetDataToHistoryDatabase(String currentDate,int caloryNumber,Context context) {
            this.caloryNumberPerDay = caloryNumber;
            this.currentDate = currentDate;
            db = new DB(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db.open();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.addEntityToHistoryDatabase(currentDate,caloryNumberPerDay);
            db.deleteAllFromTemporaryDatabase();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            db.close();
        }

    }
}




