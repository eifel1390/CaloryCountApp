package com.example.calorycountapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    public void addEntityToTemporaryDatabase(String entityName, int value, String type,int consumption){
        ContentValues cv = new ContentValues();
        cv.put(Table.TEMPORARY_ENTITY_NAME,entityName);
        cv.put(Table.TEMPORARY_ENTITY_COST,value);
        cv.put(Table.TEMPORARY_ENTITY_TYPE,type);
        cv.put(Table.TEMPORARY_ENTITY_CONSUMPTION,consumption);
        database.insertOrThrow(Table.TABLE_TEMPORARY_NAME,null,cv);
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
        productList.add(new Product("Банан", "Фрукты",95));
        productList.add(new Product("Яблоко","Фрукты",47));
        productList.add(new Product("Груша","Фрукты",95));
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

        activeList.add(new Active("Работа по дому","Домашние дела",145));
        activeList.add(new Active("Глажка белья (стоя)","Домашние дела",104));
        activeList.add(new Active("Укладка волос","Домашние дела",101));
        activeList.add(new Active("Вязание","Домашние дела",86));
        activeList.add(new Active("Одевание и раздевание, примерка","Домашние дела",86));
        activeList.add(new Active("Ручное шитье","Домашние дела",79));
        activeList.add(new Active("Чтение вслух","Домашние дела",79));
        activeList.add(new Active("Прием пищи стоя","Домашние дела",66));
        activeList.add(new Active("Персональная гигиена","Домашние дела",66));
        activeList.add(new Active("Принятие душа","Домашние дела",66));
        activeList.add(new Active("Приготовление пищи","Домашние дела",57));
        activeList.add(new Active("Разговор по телефону стоя","Домашние дела",57));
        activeList.add(new Active("Лежание без сна","Домашние дела",55));
        activeList.add(new Active("Разговор по телефону сидя","Домашние дела",36));
        activeList.add(new Active("Прием пищи сидя","Домашние дела",34));
        activeList.add(new Active("Принятие ванны","Домашние дела",34));
        activeList.add(new Active("Чтение книг сидя","Домашние дела",21));
        activeList.add(new Active("Сон","Домашние дела",32));

        activeList.add(new Active("Работа массажистом","Работа",210));
        activeList.add(new Active("Работа пильщика дров","Работа",343));
        activeList.add(new Active("Работа каменщика","Работа",286));
        activeList.add(new Active("Работа столяра","Работа",171));
        activeList.add(new Active("Работа сапожника","Работа",129));
        activeList.add(new Active("Работа в офисе","Работа",62));
        activeList.add(new Active("Сидячая работа","Работа",54));
        activeList.add(new Active("Работа массажистом","Работа",210));


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

        activeList.add(new Active("Волейбол","Командный спорт",182));
        activeList.add(new Active("Гандбол","Командный спорт",346));
        activeList.add(new Active("Футбол","Командный спорт",321));
        activeList.add(new Active("Баскетбол","Командный спорт",271));
        activeList.add(new Active("Хоккей","Командный спорт",350));

        activeList.add(new Active("Занятия балетом","Танцы",536));
        activeList.add(new Active("Бальные танцы","Танцы",196));
        activeList.add(new Active("Танцы высокой интенсивности","Танцы",346));
        activeList.add(new Active("Танцы низкой интенсивности","Танцы",154));
        activeList.add(new Active("Танцы в ритме диско","Танцы",346));
        activeList.add(new Active("Танцы диско","Танцы",286));
        activeList.add(new Active("Танцы современные","Танцы",229));
        activeList.add(new Active("Танцы медленные (вальс, танго)","Танцы",143));

        activeList.add(new Active("Аквааэробика","Водные виды спорта",379));
        activeList.add(new Active("Гребля на каноэ (4 км/ч)","Водные виды спорта",132));
        activeList.add(new Active("Гребля академическая (4 км/ч)","Водные виды спорта",150));
        activeList.add(new Active("Плавание (0,4 км/ч)","Водные виды спорта",150));
        activeList.add(new Active("Плавание (2,4 км/ч)","Водные виды спорта",329));
        activeList.add(new Active("Плавание быстрым кролем","Водные виды спорта",407));
        activeList.add(new Active("Водное поло","Водные виды спорта",429));
        activeList.add(new Active("Дайвинг","Водные виды спорта",257));
        activeList.add(new Active("Водные лыжи","Водные виды спорта",254));

        activeList.add(new Active("Прыжки через скакалку","Фитнесс и игры",386));
        activeList.add(new Active("Силовая тренировка на тренажерах","Фитнесс и игры",371));
        activeList.add(new Active("Бадминтон","Фитнесс и игры",182));
        activeList.add(new Active("Гимнастические упражнения","Фитнесс и игры",107));
        activeList.add(new Active("Йога-аштанга","Фитнесс и игры",300));
        activeList.add(new Active("Растяжка","Фитнесс и игры",90));
        activeList.add(new Active("Зарядка средней интенсивности","Фитнесс и игры",214));
        activeList.add(new Active("Настольный теннис","Фитнесс и игры",146));
        activeList.add(new Active("Игра в настольные игры","Фитнесс и игры",36));

        activeList.add(new Active("Ходьба на лыжах","Зимние виды спорта",346));
        activeList.add(new Active("Скоростной спуск на лыжах","Зимние виды спорта",193));
        activeList.add(new Active("Альпинизм","Зимние виды спорта",324));
        activeList.add(new Active("Фигурное катание","Зимние виды спорта",179));
        activeList.add(new Active("Скоростной бег на коньках","Зимние виды спорта",550));

        activeList.add(new Active("Езда на велосипеде (9 км.ч)","Транспорт",132));
        activeList.add(new Active("Езда на велосипеде (со скоростью 14 км/ч)","Транспорт",214));
        activeList.add(new Active("Езда на велосипеде (15 км/ч)","Транспорт",229));
        activeList.add(new Active("Езда на велосипеде (20 км/ч)","Транспорт",386));
        activeList.add(new Active("Ролики","Транспорт",221));
        activeList.add(new Active("Вождение автомобиля","Транспорт",72));
        activeList.add(new Active("Езда на велосипеде (9 км.ч)","Транспорт",132));
        activeList.add(new Active("Поездка на мотоцикле","Транспорт",101));
        activeList.add(new Active("Путешествие на самолете","Транспорт",66));

        activeList.add(new Active("Вытирание пыли","Уборка",57));
        activeList.add(new Active("Мытье окон","Уборка",200));
        activeList.add(new Active("Чистка стекол, зеркал","Уборка",189));
        activeList.add(new Active("Чистка сантехники","Уборка",196));
        activeList.add(new Active("Легкая уборка","Уборка",171));
        activeList.add(new Active("Чистка ковров пылесосом","Уборка",146));
        activeList.add(new Active("Мытье посуды","Уборка",100));
        activeList.add(new Active("Уборка постели","Уборка",93));
        activeList.add(new Active("Мытье полов","Уборка",93));
        activeList.add(new Active("Вытирание пыли","Уборка",57));

        activeList.add(new Active("Игра с детьми с ходьбой и бегом","Уход за ребенком",201));
        activeList.add(new Active("Игра с детьми сидя","Уход за ребенком",101));
        activeList.add(new Active("Кормление и одевание ребенка","Уход за ребенком",101));
        activeList.add(new Active("Перенос маленьких детей на руках","Уход за ребенком",134));
        activeList.add(new Active("Купание ребенка","Уход за ребенком",134));

        activeList.add(new Active("Колка дров","Дача, сад",214));
        activeList.add(new Active("Вскапывание грядок","Дача, сад",229));
        activeList.add(new Active("Сбор фруктов","Дача, сад",229));
        activeList.add(new Active("Выдергивание прошлогодней травы","Дача, сад",214));
        activeList.add(new Active("Прополка новых сорняков","Дача, сад",164));
        activeList.add(new Active("Стрижка газона","Дача, сад",143));
        activeList.add(new Active("Колка дров","Дача, сад",214));
        activeList.add(new Active("Колка дров","Дача, сад",214));
        activeList.add(new Active("Колка дров","Дача, сад",214));
        activeList.add(new Active("Колка дров","Дача, сад",214));

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
        public static final String TEMPORARY_ENTITY_CONSUMPTION = "temporary_entity_consumption";

        public static final String DB_TEMPORARY_CREATE =
                "CREATE TABLE " + TABLE_TEMPORARY_NAME + "(" +
                        TEMPORARY_ENTITY_ID + "INTEGER PRIMARY KEY, " +
                        TEMPORARY_ENTITY_NAME + " TEXT, " +
                        TEMPORARY_ENTITY_COST + " INT, " +
                        TEMPORARY_ENTITY_TYPE + " TEXT, " +
                        TEMPORARY_ENTITY_CONSUMPTION + " INT " + ")";



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
