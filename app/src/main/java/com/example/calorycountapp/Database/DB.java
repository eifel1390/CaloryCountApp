package com.example.calorycountapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.calorycountapp.Model.Active;
import com.example.calorycountapp.Model.Product;
import com.example.calorycountapp.R;

import java.util.ArrayList;
import java.util.List;

public class DB {

    private final Context mCtx;
    private DBHelper databaseHelper;
    private SQLiteDatabase database;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    public void open() {

        databaseHelper = new DBHelper(mCtx);
        database = databaseHelper.getWritableDatabase();

        if(getAllDataFromProduct()==null||getAllDataFromProduct().getCount()==0){
            database.delete(DB.Table.TABLE_NAME_PRODUCT,null,null);
            fillProductDatabaseOnDefault(database);
        }

        if(getAllDataFromActive()==null||getAllDataFromActive().getCount()==0){
            database.delete(DB.Table.TABLE_NAME_ACTIVE,null,null);
            fillActiveDatabaseOnDefault(database);
        }

    }

    public void close() {
        if (databaseHelper!=null) databaseHelper.close();
    }

    public Cursor getAllDataFromProduct() {
        return database.query(DB.Table.TABLE_NAME_PRODUCT, null, null, null, null, null, null);
    }



    public Cursor getAllDataFromActive() {
        return database.query(DB.Table.TABLE_NAME_ACTIVE, null, null, null, null, null, null);
    }

    public Cursor getAllDataFromTemporaryTable() {
        return database.query(Table.TABLE_TEMPORARY_NAME, null, null, null, null, null, null);
    }

    public Cursor getCategoryProduct(){
        String[]args = {DB.Table.CATEGORY_PRODUCT};
        return database.query(true, DB.Table.TABLE_NAME_PRODUCT,args,null,null,null,null,null,null);
    }

    public Cursor getCategoryActive(){
        String[]args = {DB.Table.CATEGORY_ACTIVE};
        return database.query(true, DB.Table.TABLE_NAME_ACTIVE,args,null,null,null,null,null,null);
    }

    public Cursor getProductListByCategory(String categoryName){

        String[] columns = {DB.Table.NAME_PRODUCT, Table.CALORICITY_PRODUCT};

        return database.query(Table.TABLE_NAME_PRODUCT,columns,"product_category = ?",
                new String[]{categoryName},null,null,null);
    }

    public Cursor getActiveListByCategory(String categoryName){

        String[] columns = {Table.NAME_ACTIVE, Table.CALORICITYCOST_ACTIVE};

        return database.query(Table.TABLE_NAME_ACTIVE,columns,"active_category = ?",
                new String[]{categoryName},null,null,null);
    }

    public Cursor getCaloryForProduct(String product){
        String [] columns = {Table.CALORICITY_PRODUCT};

        return database.query(Table.TABLE_NAME_PRODUCT,columns,"product_name = ?",
                new String[] {product},null,null,null);
    }

    public Cursor getCostForActive(String active){
        String [] columns = {Table.CALORICITYCOST_ACTIVE};

        return database.query(Table.TABLE_NAME_ACTIVE,columns,"active_name = ?",
                new String[] {active},null,null,null);
    }

    public Cursor getAllDataFromHistoryDatabase(){
        return database.query(Table.TABLE_HISTORY_NAME, null, null, null, null, null, null);
    }



    public void addProductToDatabase(String productCategory,String productName,int productCalory){
        ContentValues cv = new ContentValues();
        cv.put(Table.CATEGORY_PRODUCT,productCategory);
        cv.put(Table.NAME_PRODUCT,productName);
        cv.put(Table.CALORICITY_PRODUCT,productCalory);
        database.insert(Table.TABLE_NAME_PRODUCT,null,cv);
    }

    public void addActiveToDatabase(String activeCategory,String activeName,int activeCost){
        ContentValues cv = new ContentValues();
        cv.put(Table.CATEGORY_ACTIVE,activeCategory);
        cv.put(Table.NAME_ACTIVE,activeName);
        cv.put(Table.CALORICITYCOST_ACTIVE,activeCost);
        database.insert(Table.TABLE_NAME_ACTIVE,null,cv);
    }

    public void addEntityToTemporaryDatabase(String entityName, int value, String type){
        ContentValues cv = new ContentValues();
        cv.put(Table.TEMPORARY_ENTITY_NAME,entityName);
        cv.put(Table.TEMPORARY_ENTITY_COST,value);
        cv.put(Table.TEMPORARY_ENTITY_TYPE,type);
        database.insert(Table.TABLE_TEMPORARY_NAME,null,cv);
    }

    public void addEntityToHistoryDatabase(String currentDate,int caloryNumber){
        ContentValues cv = new ContentValues();
        cv.put(Table.HISTORY_DATE,currentDate);
        cv.put(Table.HISTORY_DATE_CALORY,caloryNumber);
        database.insert(Table.TABLE_HISTORY_NAME,null,cv);
    }

    public void deleteProductFromDatabase(String productName){
        database.delete(Table.TABLE_NAME_PRODUCT,"product_name = ?",new String[]{productName});
    }

    public void deleteActiveFromDatabase(String activeName){
        database.delete(Table.TABLE_NAME_ACTIVE,"active_name = ?",new String[]{activeName});
    }


    public void deleteEntityFromTemporaryDatabase(String entityName){
        database.delete(Table.TABLE_TEMPORARY_NAME,"temporary_entity_name = ?",new String[]{entityName});
    }

    public void deleteAllFromTemporaryDatabase(){
        database.delete(Table.TABLE_TEMPORARY_NAME,null,null);
    }

    public void deleteAllFromHistoryDatabase(){
        database.delete(Table.TABLE_HISTORY_NAME,null,null);
    }




    private void fillProductDatabaseOnDefault(SQLiteDatabase db){

        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Банан", mCtx.getString(R.string.fruits_string),95));
        productList.add(new Product("Яблоко",mCtx.getString(R.string.fruits_string),47));
        productList.add(new Product("Груша",mCtx.getString(R.string.fruits_string),95));
        productList.add(new Product("Огурец","Овощи",15));
        productList.add(new Product("Помидор","Овощи",20));
        productList.add(new Product("Лук репчатый","Овощи",47));
        productList.add(new Product("Баклажан","Овощи",25));
        productList.add(new Product("Картофель","Овощи",10));
        productList.add(new Product("Морковь","Овощи",37));
        productList.add(new Product("Капуста","Овощи",19));
        productList.add(new Product("Сельдерей","Овощи",50));
        productList.add(new Product("Петрушка","Овощи",27));
        productList.add(new Product("Чеснок","Овощи",55));
        productList.add(new Product("Укроп","Овощи",60));
        productList.add(new Product("Свекла","Овощи",17));
        productList.add(new Product("Квас","Напитки",27));
        productList.add(new Product("Пепси-кола","Напитки",38));
        productList.add(new Product("Чай черный без сахара","Напитки",0));
        productList.add(new Product("Виски","Алкоголь",235));
        productList.add(new Product("Водка","Алкоголь",235));
        productList.add(new Product("Вино красное сухое","Алкоголь",68));
        productList.add(new Product("Пончик с шоколадом","Сладости",452));
        productList.add(new Product("Батон нарезной","Хлеб и выпечка",262));
        productList.add(new Product("Щука","Рыба и морепродукты",84));
        productList.add(new Product("Сливочное масло","Масло",717));
        productList.add(new Product("Гречка","Крупы",343));
        productList.add(new Product("Тунец в масле","Консервы",190));
        productList.add(new Product("Чипсы","Снеки",536));
        productList.add(new Product("Томатный соус","Соусы и приправы",29));
        productList.add(new Product("Говядина отварная","Мясо",254));
        productList.add(new Product("Молоко 1% жирности","Молоко",42));
        productList.add(new Product("Пармезан","Сыры",431));
        productList.add(new Product("Апельсиновый сок","Соки",45));
        productList.add(new Product("Шампиньоны","Грибы",27));
        productList.add(new Product("Грецкий орех","Орехи",654));
        productList.add(new Product("Салями финская","Колбаса и копчености",502));
        productList.add(new Product("Яйцо вареное","Яйца",155));
        productList.add(new Product("Петрушка","Зелень",36));



        ContentValues cv = new ContentValues();
        for(int i=0;i< productList.size();i++){

            cv.put(DB.Table.NAME_PRODUCT,productList.get(i). getName());
            cv.put(DB.Table.CATEGORY_PRODUCT,productList.get(i). getCategoryProduct());
            cv.put(DB.Table.CALORICITY_PRODUCT,productList.get(i). getCaloricity());
            db.insert(DB.Table.TABLE_NAME_PRODUCT, null, cv);
        }
    }

    private void fillActiveDatabaseOnDefault(SQLiteDatabase db){

        List<Active> activeList = new ArrayList<>();


        activeList.add(new Active("Бег (8 км/ч)","Бег и ходьба",346));
        activeList.add(new Active("Бег (10 км/ч)","Бег и ходьба",450));
        activeList.add(new Active("Бег (16 км/ч)","Бег и ходьба",536));
        activeList.add(new Active("Бег вверх по ступенькам","Бег и ходьба",643));
        activeList.add(new Active("Бег вверх и вниз по ступенькам","Бег и ходьба",386));
        activeList.add(new Active("Бег по пересеченной местности","Бег и ходьба",429));
        activeList.add(new Active("Скоростной бег на коньках","Бег и ходьба",550));
        activeList.add(new Active("Пеший туризм (3,2 км/ч)","Бег и ходьба",107));
        activeList.add(new Active("Пеший туризм (4 км/ч)","Бег и ходьба",168));
        activeList.add(new Active("Ходьба (4 км/ч)","Бег и ходьба",129));
        activeList.add(new Active("Ходьба (6 км/ч)","Бег и ходьба",193));
        activeList.add(new Active("Спортивная ходьба","Бег и ходьба",297));
        activeList.add(new Active("Прогулка с коляской","Бег и ходьба",108));
        activeList.add(new Active("Прогулка с детьми в парке","Бег и ходьба",179));
        activeList.add(new Active("Прогулка с собакой","Бег и ходьба",143));
        activeList.add(new Active("Пешая прогулка с семьей","Бег и ходьба",72));
        activeList.add(new Active("Пешая прогулка (4,2 км/ч)","Бег и ходьба",157));
        activeList.add(new Active("Пешая прогулка (5,8 км/ч)","Бег и ходьба",225));

        activeList.add(new Active("Волейбол","Командные виды спорта",182));
        activeList.add(new Active("Гандбол","Командные виды спорта",346));
        activeList.add(new Active("Футбол","Командные виды спорта",321));
        activeList.add(new Active("Баскетбол","Командные виды спорта",271));
        activeList.add(new Active("Хоккей","Командные виды спорта",350));

        activeList.add(new Active("Занятия балетом","Танцы",536));
        activeList.add(new Active("Бальные танцы","Танцы",196));
        activeList.add(new Active("Танцы высокой интенсивности","Танцы",346));
        activeList.add(new Active("Танцы низкой интенсивности","Танцы",154));
        activeList.add(new Active("Танцы в ритме диско","Танцы",346));
        activeList.add(new Active("Танцы диско","Танцы",286));
        activeList.add(new Active("Танцы современные","Танцы",229));
        activeList.add(new Active("Танцы медленные (вальс, танго)","Танцы",143));

        ContentValues cv = new ContentValues();
        for(int i=0;i< activeList.size();i++){

            cv.put(Table.NAME_ACTIVE,activeList.get(i).getNameActive());
            cv.put(Table.CATEGORY_ACTIVE,activeList.get(i).getCategoryActive());
            cv.put(Table.CALORICITYCOST_ACTIVE,activeList.get(i).getCaloricityCost());
            db.insert(Table.TABLE_NAME_ACTIVE, null, cv);
        }
    }


    public static final class Table {

        public static final String TABLE_NAME_PRODUCT = "table_product";
        public static final String ID_PRODUCT = "product_id";
        public static final String NAME_PRODUCT = "product_name";
        public static final String CATEGORY_PRODUCT = "product_category";
        public static final String CALORICITY_PRODUCT = "product_caloricity";


        public static final String DB_PRODUCT_CREATE =
                "CREATE TABLE " + TABLE_NAME_PRODUCT + "(" +
                        ID_PRODUCT + "INTEGER PRIMARY KEY, " +
                        NAME_PRODUCT + " TEXT, " +
                        CATEGORY_PRODUCT + " TEXT, " +
                        CALORICITY_PRODUCT + " INT " + ")";

        public static final String TABLE_NAME_ACTIVE = "table_active";
        public static final String ID_ACTIVE = "active_id";
        public static final String NAME_ACTIVE = "active_name";
        public static final String CATEGORY_ACTIVE = "active_category";
        public static final String CALORICITYCOST_ACTIVE = "active_caloricitycost";


        public static final String DB_ACTIVE_CREATE =
                "CREATE TABLE " + TABLE_NAME_ACTIVE + "(" +
                        ID_ACTIVE + "INTEGER PRIMARY KEY, " +
                        NAME_ACTIVE + " TEXT, " +
                        CATEGORY_ACTIVE + " TEXT, " +
                        CALORICITYCOST_ACTIVE + " INT " + ")";


        public static final String TABLE_TEMPORARY_NAME = "temporary_table";
        public static final String TEMPORARY_ENTITY_ID = "temporary_entity_id";
        public static final String TEMPORARY_ENTITY_NAME = "temporary_entity_name";
        public static final String TEMPORARY_ENTITY_COST =
                "temporary_entity_cost";
        public static final String TEMPORARY_ENTITY_TYPE = "temporary_entity_type";


        public static final String DB_TEMPORARY_CREATE =
                "CREATE TABLE " + TABLE_TEMPORARY_NAME + "(" +
                        TEMPORARY_ENTITY_ID + "INTEGER PRIMARY KEY, " +
                        TEMPORARY_ENTITY_NAME + " TEXT, " +
                        TEMPORARY_ENTITY_COST + " INT, " +
                        TEMPORARY_ENTITY_TYPE + " TEXT " + ")";



        public static final String TABLE_HISTORY_NAME = "history_table";
        public static final String HISTORY_ID = "history_entity_id";
        public static final String HISTORY_DATE = "history_date";
        public static final String HISTORY_DATE_CALORY = "history_date_calory";

        public static final String DB_HISTORY_CREATE =
                "CREATE TABLE " + TABLE_HISTORY_NAME + "(" +
                        HISTORY_ID + "INTEGER PRIMARY KEY, " +
                        HISTORY_DATE + " TEXT, " +
                        HISTORY_DATE_CALORY + " INT " + ")";



    }

}
