module myjfx {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    opens sample to javafx.graphics, javafx.fxml;
    opens server to javafx.base, javafx.fxml,javafx.graphics;
    exports server;
    //    requires server;
//    exports myjfx.sample;
}

//module server {
//        exports server; // Export relevant packages/classes from the server module
//        // Other module configurations
//        }