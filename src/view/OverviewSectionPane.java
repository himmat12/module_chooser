package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class OverviewSectionPane extends VBox {
	// fields
	private TextArea studentProfileTextBox;
	private TextArea selectedModulesTextBox;
	private TextArea reservedModulesTextBox;

	private Button saveBtn;

	// constructors

	public OverviewSectionPane() {

		// initializing the fields
		studentProfileTextBox = new TextArea();
		selectedModulesTextBox = new TextArea();
		reservedModulesTextBox = new TextArea();

		studentProfileTextBox.setPrefHeight(160);

		saveBtn = new Button("Save Overview");

		// layout box
		var hBox = new HBox();
		HBox.setHgrow(selectedModulesTextBox, Priority.ALWAYS);
		HBox.setHgrow(reservedModulesTextBox, Priority.ALWAYS);
		hBox.setSpacing(20);
		hBox.getChildren().addAll(selectedModulesTextBox, reservedModulesTextBox);

		// parent attributes
		VBox.setVgrow(hBox, Priority.ALWAYS);

		this.setPrefSize(530, 500);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		this.setPadding(new Insets(30, 30, 30, 30));

		this.getChildren().addAll(studentProfileTextBox, hBox, saveBtn);
	}

	// methods

	/**
	 * helper method to attach event handlers to save overview btn from outside the
	 * view
	 */
	public void addSaveOverviewBtnHandler(EventHandler<ActionEvent> handler) {
		saveBtn.setOnAction(handler);
	}

	/**
	 * @return the studentProfileTextBox
	 */
	public TextArea getStudentProfileTextBox() {
		return studentProfileTextBox;
	}

	/**
	 * @return the selectedModulesTextBox
	 */
	public TextArea getSelectedModulesTextBox() {
		return selectedModulesTextBox;
	}

	/**
	 * @return the reservedModulesTextBox
	 */
	public TextArea getReservedModulesTextBox() {
		return reservedModulesTextBox;
	}

}
