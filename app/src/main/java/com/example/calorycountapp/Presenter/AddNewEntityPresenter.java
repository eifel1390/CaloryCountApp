package com.example.calorycountapp.Presenter;

import android.os.AsyncTask;

import com.example.calorycountapp.Database.DB;
import com.example.calorycountapp.EntityIdent;
import com.example.calorycountapp.View.AddNewEntity;

public class AddNewEntityPresenter  extends PresenterBase {

    private AddNewEntity activity;
    private DB db;

    public AddNewEntityPresenter(AddNewEntity activity) {
        this.activity = activity;
        db = new DB(activity);
    }

    public void saveEntityToDatabase(String category, String name, String calory,String type){
        NewEntityAddingPresenterTask task =new NewEntityAddingPresenterTask(db,type);
        task.execute(category,name,calory);
        activity.returnToPreviouslyActivity();
    }

    @Override
    public void displayAnotherScreen(String nameOfScreen, String entityIdent) {}

    @Override
    public void viewIsReady(String ident) {}

    private class NewEntityAddingPresenterTask extends AsyncTask<String, Void, Void> {

        DB db;
        String entityIdent;

        public NewEntityAddingPresenterTask(DB db, String entityValue) {
            this.db = db;
            this.entityIdent = entityValue;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db.open();
        }

        @Override
        protected Void doInBackground(String... params) {
            if(entityIdent.equals(EntityIdent.IS_PRODUCT)) {
                db.addProductToDatabase(params[0], params[1], Integer.parseInt(params[2]));
            }
            else if(entityIdent.equals(EntityIdent.IS_ACTIVE)) {
                db.addActiveToDatabase(params[0],params[1],Integer.parseInt(params[2]));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            db.close();
        }
    }
}

