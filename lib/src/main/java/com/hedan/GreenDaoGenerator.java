package com.hedan;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class GreenDaoGenerator {

    public static void main(String[] args) throws Exception {
        //Schema对象接受2个参数，第一个参数是DB的版本号，通过更新版本号来更新数据库。
        // 第二个参数是自动生成代码的包路径。包路径系统自动生成
        Schema schema = new Schema(1, "com.hedan.dao");
        addNote(schema);
        addCustomerOrder(schema);

        /**
         * 第一个参数是Schema对象，第二个参数是希望自动生成的代码对应的项目路径。
         试了下src-gen这个文件夹必须手动创建,这里路径如果错了会抛出异常。
         好了先别慌运行这段程序。新建一个Android项目名字是DaoExample，和刚才的JAVA项目保持在同一个文件夹下。
         接着就可以运行刚才的JAVA程序，会看到src-gen下面自动生成了8个文件，3个实体对象，3个dao，1个DaoMaster,
         1个DaoSession
         */
        new DaoGenerator().generateAll(schema, "app/src/main/java-gen");
    }

    private static void addCustomerOrder(Schema schema) {

        Entity customer = schema.addEntity("Customer");
        customer.addIdProperty();
        customer.addStringProperty("name").notNull();

        Entity order = schema.addEntity("order");
        order.setTableName("ORDERS");
        order.addIdProperty();
        Property orderDate = order.addDateProperty("date").getProperty();
        Property customerId = order.addLongProperty("customerId").getProperty();
        order.addToOne(customer, customerId);

        ToMany customerToOrders = customer.addToMany(order, customerId);
        customerToOrders.setName("orders");
        customerToOrders.orderAsc(orderDate);

    }

    private static void addNote(Schema schema) {
        /**
         Entity表示一个实体可以对应成数据库中的表
         系统自动会以传入的参数作为表的名字，这里表名就是NOTE
         当然也可以自己设置表的名字，像这样：
         order.setTableName("ORDERS");
         */
        Entity note = schema.addEntity("Note");
        /**
         * 接下来是一些字段参数设置。
         如果想ID自动增长可以像这样：
         order.addIdProperty().autoincrement();
         */
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addDateProperty("date");
    }
}
