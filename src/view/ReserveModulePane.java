package view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class ReserveModulePane extends VBox {
	// fields

	// labels
	private Label unselectedLabel, reservedLabel, buttonsLabel;

	// buttons
	private Button addBtn, removeBtn, confirmBtn;

	// modules list view
	private ListView<String> unselectedModulesListview, reservedModulesListview;

	// constructors

	public ReserveModulePane() {

		// labels
		buttonsLabel = new Label("Reserve one opitonal module");

		// buttons
		addBtn = new Button("Add");
		removeBtn = new Button("Remove");
		confirmBtn = new Button("Confirm");

		addBtn.setPrefWidth(70);

		// listviews
		unselectedModulesListview = new ListView<String>();
		reservedModulesListview = new ListView<String>();

		// grid pane
		var grid = new GridPane();
		grid.setVgap(20);
		grid.setHgap(20);
		grid.setPadding(new Insets(30, 30, 10, 30));
//		 grid.setGridLinesVisible(true);

		// Create Column Constraints
		for (int i = 0; i < 2; i++) {
			ColumnConstraints colConstraints = new ColumnConstraints();
			colConstraints.setHgrow(Priority.ALWAYS); // Allow column to grow horizontally
			grid.getColumnConstraints().add(colConstraints);
		}

		// Create Row Constraints
		RowConstraints rowConstraints = new RowConstraints();
		rowConstraints.setVgrow(Priority.ALWAYS); // Allow row to grow vertically
		grid.getRowConstraints().add(rowConstraints);

		// block 3/4 unselected modules
		unselectedLabel = new Label("Unselected Block 3/4 Modules");
		unselectedModulesListview.setPrefHeight(150);

		var unselectedBlockVbox = new VBox();
		VBox.setVgrow(unselectedModulesListview, Priority.ALWAYS);
		unselectedModulesListview.setMaxHeight(Double.MAX_VALUE);
		unselectedBlockVbox.setSpacing(10);
		unselectedBlockVbox.getChildren().addAll(unselectedLabel, unselectedModulesListview);

		grid.add(unselectedBlockVbox, 0, 0);

		// block 3/4 reserved modules
		reservedLabel = new Label("Reserved Block 3/4 Modules");
		reservedModulesListview.setPrefHeight(150);

		var reservedBlockVbox = new VBox();
		VBox.setVgrow(reservedModulesListview, Priority.ALWAYS);
		reservedModulesListview.setMaxHeight(Double.MAX_VALUE);
		reservedBlockVbox.setSpacing(10);
		reservedBlockVbox.getChildren().addAll(reservedLabel, reservedModulesListview);

		grid.add(reservedBlockVbox, 1, 0);

		// bottom components
		var bottomBox = new HBox();

		bottomBox.setAlignment(Pos.CENTER);
		bottomBox.setSpacing(20);
		bottomBox.setPadding(new Insets(20, 20, 40, 20));
		bottomBox.getChildren().addAll(buttonsLabel, addBtn, removeBtn, confirmBtn);

		this.setPrefSize(530, 500);
		this.getChildren().addAll(grid, bottomBox);

		VBox.setVgrow(grid, Priority.ALWAYS);

	}

	// methods

	// Add event handler for submit button
	public void addConfirmBtnHandler(EventHandler<ActionEvent> handler) {
		confirmBtn.setOnAction(handler);
	}

	// Add event handler for add button
	public void addAddBtnHandler(EventHandler<ActionEvent> handler) {
		addBtn.setOnAction(handler);
	}

	// Add event handler for remove button
	public void addRemoveBtnHandler(EventHandler<ActionEvent> handler) {
		removeBtn.setOnAction(handler);
	}

	/**
	 * adds the unselected modules for unselected listview
	 */
	public void addUnselectedModules(ObservableList<String> modules) {

		for (String module : modules) {
			unselectedModulesListview.getItems().add(module);
		}
	}

	/**
	 * adds the reserved modules for reserved listview
	 */
	public void addReservedModules(ObservableList<String> modules) {

		for (String module : modules) {
			reservedModulesListview.getItems().add(module);
		}
	}

	/**
	 * @return the unselectedModulesListview
	 */
	public ListView<String> getUnselectedModulesListview() {
		return unselectedModulesListview;
	}

	/**
	 * @return the reservedModulesListview
	 */
	public ListView<String> getReservedModulesListview() {
		return reservedModulesListview;
	}

}
