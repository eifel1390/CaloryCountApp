package com.example.calorycountapp.Presenter;

public class CategoryPresenter extends PresenterBase {

    private Category category;
    private DB model;
    private Context context;

    public CategoryPresenter(MvpView mvpView) {
        this.category = (Category)mvpView;
        this.context = category;
        model = new DB(context);
    }

    //в момент включения фрагмент запрашивает данные либо по продуктам либо по активностям
    @Override
    public void viewIsReady(String ident) {
        if(ident.equals(EntityIdent.IS_PRODUCT)) {
            LoadCategoryListTask task = new LoadCategoryListTask(model,EntityIdent.IS_PRODUCT);
            showDataInView(task);
        }

        if(ident.equals(EntityIdent.IS_ACTIVE)) {
            LoadCategoryListTask task = new LoadCategoryListTask(model,EntityIdent.IS_ACTIVE);
            showDataInView(task);
        }
    }

    //отображение полученных данных во вью Category
    public void showDataInView(LoadCategoryListTask task){
        task.execute();
        try {
            category.showData(task.get());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    //при нажатии на категорию отображается список продуктов и активностей по категории
    @Override
    public void displayAnotherScreen(String nameOfScreen,String ident) {
        Intent intent = new Intent(category,CategoryDetail.class);
        intent.putExtra(CategoryDetail.CATEGORY_DETAIL,nameOfScreen);
        intent.putExtra(CategoryDetail.ENTITY_IDENT,ident);
        category.startActivity(intent);
    }

    private class LoadCategoryListTask extends AsyncTask<Void, Void, List<Entity>> {

        DB db;
        String loadId;

        LoadCategoryListTask(DB db,String loadId){
            this.db = db;
            this.loadId = loadId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db.open();
        }

        @Override
        protected List<Entity> doInBackground(Void... params) {
            List<Entity>entityList = new ArrayList<>();
            if(loadId.equals(EntityIdent.IS_PRODUCT)) {
                Cursor c = db.getCategoryProduct();
                if (c.moveToFirst()) {
                    int idCategoryIndex = c.getColumnIndex(DB.Table.CATEGORY_PRODUCT);

                    do {
                        Product product = new Product();
                        product.setCategoryProduct(c.getString(idCategoryIndex));
                        entityList.add(product);
                    }
                    while (c.moveToNext());
                }
                c.close();
            }
            else if(loadId.equals(EntityIdent.IS_ACTIVE)){

                Cursor c = db.getCategoryActive();
                if (c.moveToFirst()) {
                    int idCategoryIndex = c.getColumnIndex(DB.Table.CATEGORY_ACTIVE);

                    do {
                        Active active = new Active();
                        active.setCategoryActive(c.getString(idCategoryIndex));
                        entityList.add(active);

                    }
                    while (c.moveToNext());
                }
                c.close();
            }
            return entityList;
        }

        @Override
        protected void onPostExecute(List<Entity> list) {
            super.onPostExecute(list);
            db.close();
        }
    }
}

