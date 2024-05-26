module com.connectfour.connectfourgame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires jdk.sctp;

    opens com.connectfour.connectfourgame to javafx.fxml;
    exports com.connectfour.connectfourgame;
}