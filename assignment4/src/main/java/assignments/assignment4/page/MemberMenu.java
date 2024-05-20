package assignments.assignment4.page;

import javafx.scene.Scene;
import javafx.scene.control.Alert;

public abstract class MemberMenu {
    private Scene scene;
    abstract protected Scene createBaseMenu();

    // Menampilkan alert dengan informasi yang diberikan.
    protected void showAlert(String title, String header, String content, Alert.AlertType c){
        Alert alert = new Alert(c);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Mengembalikan scene yang terkait dengan menu ini.
    public Scene getScene(){
        return this.scene;
    }

    //  Merefresh data dan menampilkan alert informasi bahwa data telah diperbarui.
    protected void refresh(){
        showAlert("Refresh", null, "Data telah diperbarui.", Alert.AlertType.INFORMATION);
    }
}
