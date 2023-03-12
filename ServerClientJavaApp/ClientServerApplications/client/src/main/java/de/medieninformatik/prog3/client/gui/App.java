package de.medieninformatik.prog3.client.gui;

import de.medieninformatik.prog3.client.model.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author <Janik Hagen> <m29127>
 * date:2022-02-27
 * Programmierung 3,  Hausarbeit
 * Beschreibung der Klasse:
 * Klasse die sich um das Setup der Gui kuemmert
 */
public class App extends Application {
    // CRUD interface
    private CRUDMovieData crudImpl = null;
    //controls
    private TextField txtSearch;
    private TextField txtTitel;
    private TextField txtGenre;
    private TextField txtDirector;
    private TextField txtRating;
    private DatePicker dtReleaseDate;
    private int saveId;

    private ScrollPane scrollPane;
    private TableView tableView;


    /**
     * Methode um ein Date in ein LocalDate zu konvertieren
     * @param dateToConvert zu konvertierendes Datum
     * @return Instant.ofEpochMilli(dateToConvert.getTime ())
     *                 .atZone(ZoneId.systemDefault())
     *                 .toLocalDate()
     */
    public LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * Methode um ein LocalDate in ein Date zu konvertieren
     * @param dateToConvert
     * @returnjava.util.Date.from(dateToConvert.atStartOfDay()
     *                 .atZone(ZoneId.systemDefault ())
     *                 .toInstant())
     */
    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    /**
     * Methode die das komplette Interface des JavaFX basieren Clients beinhaltet, baut das Anwendungsfenster auf
     * @param primaryStage Haupt Stage der Anwendung
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        crudImpl = CRUDFactory.getInstance(CRUDFactory.CRUDTypes.rest);

        //primary Stage
        VBox vBox = new VBox();

        HBox hTop = new HBox();
        VBox vLeft = new VBox();
        VBox vMid = new VBox();
        VBox vRight = new VBox();

        HBox hMid = new HBox();
        HBox hBot = new HBox();

        //login
        HBox logBox = new HBox();

        //Searchbar
        txtSearch = new TextField();
        txtSearch.setPromptText("Suchen");
        txtSearch.setTooltip(new Tooltip("Suchfeld"));
        Button btnSearch = new Button("Suchen");
        btnSearch.setOnAction(new ButtonListenerSearch());

        //Top
        hTop.getChildren().addAll(txtSearch, btnSearch);
        hTop.setHgrow(txtSearch, Priority.ALWAYS);
        hTop.setPadding(new Insets(10, 10, 20, 10));

        //Mid - Left
        Label titel = new Label("Titel");
        titel.setPadding(new Insets(2, 0, 0, 0));
        Label genre = new Label("Genre");
        genre.setPadding(new Insets(9, 0, 0, 0));
        Label director = new Label("Regisseur");
        director.setPadding(new Insets(9, 0, 0, 0));
        Label rating = new Label("Bewertung");
        rating.setPadding(new Insets(9, 0, 0, 0));
        Label releaseDate = new Label("Erscheinungsdatum");
        releaseDate.setPadding(new Insets(9, 0, 0, 0));

        vLeft.getChildren().addAll(titel, genre, director, rating, releaseDate);
        vLeft.setPadding(new Insets(0, 10, 0, 10));

        //Mid - Mid

        txtTitel = new TextField();
        txtTitel.setMinWidth(400);
        txtGenre = new TextField();
        txtDirector = new TextField();
        txtRating = new TextField();
        dtReleaseDate = new DatePicker();

        vMid.getChildren().addAll(txtTitel, txtGenre, txtDirector, txtRating, dtReleaseDate);
        vMid.setPadding(new Insets(0, 10, 0, 10));

        //Mid - Right
        Button btnAdd = new Button("Hinzufuegen");
        btnAdd.setMinWidth(120);
        btnAdd.setOnAction(new ButtonListenerAdd());                 //todo

        Button btnDelete = new Button("Loeschen");
        btnDelete.setMinWidth(120);
        btnDelete.setOnAction(new ButtonListenerDelete());              //todo

        Button btnEdit = new Button("Editieren");
        btnEdit.setMinWidth(120);
        btnEdit.setOnAction(new ButtonListenerEdit());                //todo

        vRight.getChildren().addAll(btnAdd, btnDelete, btnEdit);

        //combine to hMid

        Button btnAdmin = new Button("Admin");
        Button btnUser = new Button("Nutzer");
        btnAdmin.setOnAction(e -> {
            //adminRights = true;
            hMid.getChildren().addAll(vLeft, vMid, vRight);
            btnAdmin.setDisable(true);
            btnUser.setDisable(true);
        });
        btnUser.setOnAction(e -> {
            //adminRights = false;
            hMid.getChildren().addAll(vLeft, vMid);
            btnAdmin.setDisable(true);
            btnUser.setDisable(true);
            txtTitel.setEditable(false);
            txtGenre.setEditable(false);
            txtDirector.setEditable(false);
            txtRating.setEditable(false);
            dtReleaseDate.setEditable(false);
        });

        logBox.getChildren().addAll(btnAdmin, btnUser);
        /*
        if (adminRights == false) {
            hMid.getChildren().addAll(vLeft, vMid);
        } else {
            hMid.getChildren().addAll(vLeft, vMid, vRight);
        }*/

        //Bot
        scrollPane = new ScrollPane();
        tableView = new TableView<>();
        tableView.getSelectionModel().selectedItemProperty().addListener((observerable, oldSelection, newSelection) -> {
            if (newSelection instanceof MovieData) {
                MovieData movieData = (MovieData)newSelection;
                txtTitel.setText(movieData.getTitle());
                txtGenre.setText(movieData.getGenre());
                txtDirector.setText(movieData.getDirector());
                txtRating.setText(String.valueOf(movieData.getRating()));
                LocalDate ld = convertToLocalDateViaMilisecond(movieData.getReleaseDate());
                dtReleaseDate.setValue(ld);
                saveId = movieData.getUniqueId();
            }
        });

        TableColumn<MovieData, String> column1 = new TableColumn<>("Titel");
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<MovieData, String> column2 = new TableColumn<>("Genre");
        column2.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn<MovieData, String> column3 = new TableColumn<>("Regisseur");
        column3.setCellValueFactory(new PropertyValueFactory<>("director"));

        TableColumn<MovieData, Integer> column4 = new TableColumn<>("Bewertung");
        column4.setCellValueFactory(new PropertyValueFactory<>("rating"));

        TableColumn<MovieData, Date> column5 = new TableColumn<>("Erscheinungsdatum");
        column5.setMinWidth(120);
        column5.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        column5.setCellFactory(column -> {
            TableCell<MovieData, Date> cell = new TableCell<>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(format.format(item));
                    }
                }
            };

            return cell;
        });

        tableView.getColumns().addAll(column1, column2, column3, column4, column5);

        scrollPane.setContent(tableView);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        //scrollPane.setMinWidth(450);

        hBot.getChildren().addAll(scrollPane);
        hBot.setPadding(new Insets(10, 5, 5, 5));
        hBot.setHgrow(scrollPane, Priority.ALWAYS);
        vBox.getChildren().addAll(logBox, hTop, hMid, hBot);
        VBox.setVgrow(hBot, Priority.ALWAYS);

        updateMovieData();

        Scene scene = new Scene(vBox, 670, 600);
        primaryStage.setTitle("Client: JavaFx Database Viewer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Methode die die dargestellten Inhalte von tableView neu laedt
     */
    private void updateMovieData() {
        String filter = txtSearch.getText();
        MovieList movieList = crudImpl.readMovieList(filter);
        tableView.getItems().clear();
        movieList.get().forEach((item) -> {
            tableView.getItems().add(item);
        });
        tableView.getSelectionModel().select(0);
    }

    /**
     * Klasse, die sich um die Funktionalitaet des "Suchen"-Buttons kuemmert
     */
    private class ButtonListenerSearch implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            System.out.println("--> search: " + txtSearch.getText());
            updateMovieData();
        }
    }

    /**
     * Klasse, die sich um die Funktionalitaet des "Hinzufuegen"-Buttons kuemmert
     */
    private class ButtonListenerAdd implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            System.out.println("--> add : " + txtTitel.getText());


            MovieData movieData = new MovieData();
            movieData.setTitle(txtTitel.getText());
            movieData.setDirector(txtDirector.getText());
            movieData.setGenre(txtGenre.getText());
            LocalDate ld = dtReleaseDate.getValue();
            Date releaseDate = convertToDateViaInstant(ld);
            movieData.setReleaseDate(releaseDate);
            movieData.setRating(5);                                                 //todo
            crudImpl.createMovieData(movieData);
            updateMovieData();
        }
    }

    /**
     * Klasse, die sich um die Funktionalitaet des "Loeschen"-Buttons kuemmert
     */
    private class ButtonListenerDelete implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            System.out.println("--> delete : " + txtTitel.getText());
                    crudImpl.deleteMovieData(saveId);

            updateMovieData();
        }
    }

    /**
     * Klasse, die sich um die Funktionalitaet des "Editieren"-Buttons kuemmert
     */
    private class ButtonListenerEdit implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            System.out.println("--> add : " + txtTitel.getText());

            MovieData movieData = new MovieData();
            movieData.setUniqueId(saveId);
            movieData.setTitle(txtTitel.getText());
            movieData.setDirector(txtDirector.getText());
            movieData.setGenre(txtGenre.getText());
            LocalDate ld = dtReleaseDate.getValue();
            Date releaseDate = convertToDateViaInstant(ld);
            movieData.setReleaseDate(releaseDate);
            movieData.setRating(5);                                                 //todo
            crudImpl.updateMovieData(movieData);
            updateMovieData();
        }
    }

}


