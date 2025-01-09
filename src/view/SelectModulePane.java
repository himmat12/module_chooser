package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class SelectModulePane extends VBox {

	// fields

	// grid pane
	private GridPane grid;

	// labels
	private Label block1Label, block2Label, unselectedLabel, selectedLabel, blockLabel, creditsLabel;

	// buttons
	private Button addBtn, removeBtn, resetBtn, submitBtn;

	// modules list view
	private ListView<String> block1ModuleList, block2ModuleList, unselectedModuleList, selectedModuleList;

	// text field
	private TextField creditsField;

	// constructors
	public SelectModulePane() {

		// grid pane
		grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(20);
		grid.setPadding(new Insets(6, 16, 16, 16));
		// grid.setGridLinesVisible(true);

		// Create Column Constraints
		for (int i = 0; i < 2; i++) {
			ColumnConstraints colConstraints = new ColumnConstraints();
			colConstraints.setHgrow(Priority.ALWAYS); // Allow column to grow horizontally
			grid.getColumnConstraints().add(colConstraints);
		}

		// Create Row Constraints
		for (int i = 0; i < 2; i++) {
			RowConstraints rowConstraints = new RowConstraints();
			rowConstraints.setVgrow(Priority.ALWAYS); // Allow row to grow vertically
			grid.getRowConstraints().add(rowConstraints);
		}

		// block 1 selected modules
		block1Label = new Label("Selected Block 1 Modules");
		block1ModuleList = new ListView<String>();
		block1ModuleList.setPrefHeight(150);

		var block1vBox = new VBox();
		block1vBox.setSpacing(10);
		block1vBox.getChildren().addAll(block1Label, block1ModuleList);
		VBox.setVgrow(block1ModuleList, Priority.ALWAYS);

		grid.add(block1vBox, 0, 0);

		// block 2 selected modules
		block2Label = new Label("Selected Block 2 Modules");
		block2ModuleList = new ListView<String>();
		block2ModuleList.setPrefHeight(150);

		var block2vBox = new VBox();
		block2vBox.setSpacing(10);
		block2vBox.getChildren().addAll(block2Label, block2ModuleList);
		VBox.setVgrow(block2ModuleList, Priority.ALWAYS);

		grid.add(block2vBox, 0, 1);

		// block 3/4 unselected modules
		VBox unselectedvBox = new VBox();
		HBox addRemoveBtnContainer = new HBox();

		unselectedLabel = new Label("Unselected Block 3/4 Modules");
		unselectedModuleList = new ListView<String>();
		unselectedModuleList.setPrefHeight(150);

		blockLabel = new Label("Block 3/4");
		addBtn = new Button("Add");
		removeBtn = new Button("Remove");
		addBtn.setPrefWidth(60);
		addRemoveBtnContainer.getChildren().addAll(blockLabel, addBtn, removeBtn);
		addRemoveBtnContainer.setAlignment(Pos.CENTER);
		addRemoveBtnContainer.setSpacing(12);

		unselectedvBox.getChildren().addAll(unselectedLabel, unselectedModuleList, addRemoveBtnContainer);
		unselectedvBox.setSpacing(10);
		VBox.setVgrow(unselectedModuleList, Priority.ALWAYS);

		grid.add(unselectedvBox, 1, 0);

		// block 3/4 selected modules
		selectedLabel = new Label("Selected Block 3/4 Modules");
		selectedModuleList = new ListView<String>();
		selectedModuleList.setPrefHeight(150);

		var selectedvBox = new VBox();
		selectedvBox.setSpacing(10);
		selectedvBox.getChildren().addAll(selectedLabel, selectedModuleList);
		VBox.setVgrow(selectedModuleList, Priority.ALWAYS);

		grid.add(selectedvBox, 1, 1);

		// bottom components: credits field and add/remove buttons
		HBox hBox1 = new HBox();

		creditsLabel = new Label("Current credits:");
		creditsField = new TextField();
		creditsField.setPrefWidth(60);
		creditsField.setDisable(true);
		creditsField.setStyle("-fx-text-fill: black; -fx-opacity: 1;");

		hBox1.setAlignment(Pos.CENTER);
		hBox1.setSpacing(12);
		hBox1.getChildren().addAll(creditsLabel, creditsField);

		HBox hBox2 = new HBox();

		resetBtn = new Button("Reset");
		submitBtn = new Button("Submit");

		resetBtn.setPrefWidth(70);
		submitBtn.setPrefWidth(70);

		hBox2.setAlignment(Pos.CENTER);
		hBox2.setSpacing(12);
		hBox2.getChildren().addAll(resetBtn, submitBtn);

		// vBox attributes
		this.setPrefSize(530, 500);
		this.setPadding(new Insets(16, 16, 16, 16));
		this.setAlignment(Pos.CENTER);
		this.setSpacing(12);
		this.getChildren().addAll(grid, hBox1, hBox2);

	}
	// methods

	// Add event handler for submit button
	public void addSubmitBtnHandler(EventHandler<ActionEvent> handler) {
		submitBtn.setOnAction(handler);
	}

	// Add event handler for add button
	public void addAddBtnHandler(EventHandler<ActionEvent> handler) {
		addBtn.setOnAction(handler);
	}

	// Add event handler for reset button
	public void addResetBtnHandler(EventHandler<ActionEvent> handler) {
		resetBtn.setOnAction(handler);
	}

	// Add event handler for remove button
	public void addRemoveBtnHandler(EventHandler<ActionEvent> handler) {
		removeBtn.setOnAction(handler);
	}

	/**
	 * adds the selected modules for block 1 listview
	 */
	public void addBlock1SelectedModules(ArrayList<String> modules) {

		for (String module : modules) {
			block1ModuleList.getItems().add(module);
		}
	}

	/**
	 * clears the selected modules for block 1 listview
	 */
	public void clearBlock1SelectedModules() {

		block1ModuleList.getItems().clear();
	}

	/**
	 * adds the selected modules for block 2 listview
	 */
	public void addBlock2SelectedModules(ObservableList<String> modules) {

		for (String module : modules) {
			block2ModuleList.getItems().add(module);
		}
	}

	/**
	 * adds the unselected modules for block 3/4 listview
	 */
	public void addBlock3and4UnselectedModules(ObservableList<String> modules) {

		for (String module : modules) {
			unselectedModuleList.getItems().add(module);
		}
	}

	/**
	 * adds the unselected modules for block 3/4 listview
	 */
	public void addBlock3and4SelectedModules(ObservableList<String> modules) {

		for (String module : modules) {
			selectedModuleList.getItems().add(module);
		}
	}

	/**
	 * @return the grid
	 */
	public GridPane getGrid() {
		return grid;
	}

	/**
	 * @return the addBtn
	 */
	public Button getAddBtn() {
		return addBtn;
	}

	/**
	 * @return the removeBtn
	 */
	public Button getRemoveBtn() {
		return removeBtn;
	}

	/**
	 * @return the resetBtn
	 */
	public Button getResetBtn() {
		return resetBtn;
	}

	/**
	 * @return the submitBtn
	 */
	public Button getSubmitBtn() {
		return submitBtn;
	}

	/**
	 * @return the block1ModuleList
	 */
	public ListView<String> getblock1ModuleList() {
		return block1ModuleList;
	}

	/**
	 * @return the block2ModuleList
	 */
	public ListView<String> getblock2ModuleList() {
		return block2ModuleList;
	}

	/**
	 * @return the unselectedModuleList
	 */
	public ListView<String> getUnselectedModuleList() {
		return unselectedModuleList;
	}

	/**
	 * @return the selectedModuleList
	 */
	public ListView<String> getSelectedModuleList() {
		return selectedModuleList;
	}

	/**
	 * @return the creditsField
	 */
	public TextField getcreditsField() {
		return creditsField;
	}

}