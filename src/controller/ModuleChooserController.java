package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;
import model.Block;
import model.Course;
import model.Module;
import model.Storage;
import model.StudentProfile;
import view.ModuleChooserRootPane;
import view.OverviewSectionPane;
import view.ReserveModulePane;
import view.SelectModulePane;
import view.AlertBox;
import view.CreateStudentProfilePane;
import view.ModuleChooserMenuBar;

public class ModuleChooserController {

	// fields to be used throughout class
	private StudentProfile studentProfile;
	private ModuleChooserRootPane view;

	private CreateStudentProfilePane cspp;
	private SelectModulePane smp;
	private ReserveModulePane rmp;
	private OverviewSectionPane osp;
	private ModuleChooserMenuBar mstmb;

	// modules maps
	Map<String, Module> block1Modules;
	Map<String, Module> block2Modules;
	Map<String, Module> selectedModules;
	Map<String, Module> unselectedModules;

	// modules lists

	// select module pane state - each block modules
	ObservableList<String> block1ModulesList;
	ObservableList<String> block2ModulesList;
	ObservableList<String> selectedModuleList;
	ObservableList<String> unselectedModuleList;

	// reserve module pane state - reserved modules
	Map<String, Module> reservedModules;
	Map<String, Module> reservedUnselectedModules;
	ObservableList<String> reservedModuleList;
	ObservableList<String> reservedUnselectedModulesList;

	// current credits
	int currentCredits;

	// cources
	Course[] courses;

	// constants values
	private final String COMPUTER_SCI = "Computer Science", SOFTWARE_ENG = "Software Engineering";

	public ModuleChooserController(StudentProfile model, ModuleChooserRootPane view) {

		// initialise model and view fields
		this.studentProfile = model;
		this.view = view;

		this.block1Modules = new HashMap<String, Module>(); // block 1 selected modules
		this.block2Modules = new HashMap<String, Module>(); // block 1 selected modules
		this.selectedModules = new HashMap<String, Module>(); // block 3 and 4 selected modules
		this.unselectedModules = new HashMap<String, Module>(); // block 3 and 4 unselected modules

		this.block1ModulesList = FXCollections.observableArrayList();
		this.block2ModulesList = FXCollections.observableArrayList();
		this.selectedModuleList = FXCollections.observableArrayList();
		this.unselectedModuleList = FXCollections.observableArrayList();

		this.reservedUnselectedModules = new HashMap<String, Module>(); // for unselected reserved module
		this.reservedModules = new HashMap<String, Module>(); // for reserved module
		this.reservedModuleList = FXCollections.observableArrayList();
		this.reservedUnselectedModulesList = FXCollections.observableArrayList();

		this.currentCredits = 0; // initializing it with 0

		this.courses = generateAndGetCourses(); // generating courses

		// initialise view subcontainer fields
		cspp = view.getCreateStudentProfilePane();
		smp = view.getSelectModulePane();
		rmp = view.getReserveModulePane();
		osp = view.getOverviewSectionPane();
		mstmb = view.getModuleSelectionToolMenuBar();

		// add courses to combobox in create student profile pane using the
		// generateAndGetCourses helper method below
		cspp.addCourseDataToComboBox(generateAndGetCourses());

		// attach event handlers to view using private helper method
		this.attachEventHandlers();
	}

	/**
	 * helper method - used to attach event handlers
	 */
	private void attachEventHandlers() {
		// attach an event handler to the create student profile pane
		cspp.addCreateStudentProfileHandler(new CreateStudentProfileHandler());

		///////////////////////////////////////////////////////////////////////

		// SMP event handler //

		// add btn handler
		smp.addAddBtnHandler(e -> smpAddBtnHandler());
		// remove btn handler
		smp.addRemoveBtnHandler(e -> smpRemoveBtnHandler());

		// reset btn handler
		smp.addResetBtnHandler(e -> smpResetBtnHandler());

		// submit btn handler
		smp.addSubmitBtnHandler(e -> smpSubmitBtnHandler());

		///////////////////////////////////////////////////////////////////////

		// RMP event handler //

		// add btn handler
		rmp.addAddBtnHandler(e -> rmpAddBtnHandler());

		// add remove handler
		rmp.addRemoveBtnHandler(e -> rmpRemoveBtnHandler());

		// add confirm handler
		rmp.addConfirmBtnHandler(e -> rmpConfirmBtnHandler());

		///////////////////////////////////////////////////////////////////////

		// OSP event handler //
		osp.addSaveOverviewBtnHandler(e -> ospSaveBtnHandler());

		///////////////////////////////////////////////////////////////////////

		// MSTMB event handlers //

		// attach an event handler to the menu bar that closes the application
		mstmb.addExitHandler(e -> System.exit(0));

		// save student profile data btn handler
		mstmb.addSaveHandler(e -> mstmbSaveProfileHandler());

		// load student profile data btn handler
		mstmb.addLoadHandler(e -> mstmbLoadStudentDataHandler());

		// show about info data btn handler
		mstmb.addAboutHandler(e -> mstmbHelpMenuItemHandler());
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * CREATE STUDENT PANE
	 */

	/**
	 * event handler (currently empty), which can be used for creating a profile
	 */
	private class CreateStudentProfileHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			// input validation - every input fields needs to be filled in order to validate
			if (!cspp.getStudentPnumber().isEmpty() && !cspp.getTxtFirstName().getText().isEmpty()
					&& !cspp.getTxtSurname().getText().isEmpty() && !cspp.getTxtEmail().getText().isEmpty()
					&& cspp.getInputDate().getValue() != null) {

				// reinitializing student profile for fresh data
				studentProfile = new StudentProfile();

				// updating student profile
				studentProfile.setStudentCourse(cspp.getSelectedCourse());
				studentProfile.setStudentName(cspp.getStudentName());
				studentProfile.setStudentEmail(cspp.getStudentEmail());
				studentProfile.setStudentPnumber(cspp.getStudentPnumber().toUpperCase());
				studentProfile.setSubmissionDate(cspp.getStudentDate());

				// initializing the initial state of the smp view
				getModulesInitialState();

				view.changeTab(1);

			} else

			{
				new AlertBox(AlertType.WARNING, "Info", "Some fields are empty.",
						"All input fields should be entered in order to create student profile!");
			}

		}

	}

	// helper methods

	/**
	 * helper method which resets the state of smp, rmp and osp view and populates
	 * the initial state in the smp
	 */
	private void getModulesInitialState() {
		// clearing module data
		block1ModulesList.clear();
		block2ModulesList.clear();
		unselectedModuleList.clear();
		selectedModuleList.clear();

		block1Modules.clear();
		block2Modules.clear();
		unselectedModules.clear();
		selectedModules.clear();

		reservedUnselectedModulesList.clear();
		reservedModuleList.clear();

		reservedUnselectedModules.clear();
		reservedModules.clear();

		// updating the model (student profile data)
		studentProfile.getAllSelectedModules().clear();
		studentProfile.getAllReservedModules().clear();

		final ArrayList<Module> modulesList = new ArrayList<Module>();

		ArrayList<Module> block1 = new ArrayList<Module>();
		ArrayList<Module> block2 = new ArrayList<Module>();
		ArrayList<Module> unselectedList = new ArrayList<Module>();
		ArrayList<Module> selectedList = new ArrayList<Module>();

		var selectedCourse = cspp.getSelectedCourse().getCourseName();

		if (selectedCourse.equalsIgnoreCase(COMPUTER_SCI)) {
			courses[0].getAllModulesOnCourse().forEach(e -> modulesList.add(e));
		}

		if (selectedCourse.equalsIgnoreCase(SOFTWARE_ENG)) {
			courses[1].getAllModulesOnCourse().forEach(e -> modulesList.add(e));
		}

		for (Module module : modulesList) {
			if (module.getRunPlan() == Block.BLOCK_1) {
				block1.add(module);
			}

			if (module.getRunPlan() == Block.BLOCK_2) {
				block2.add(module);
			}

			if (module.getRunPlan() == Block.BLOCK_3_4 && module.isMandatory() == true) {
				selectedList.add(module);
			}

			if (module.getRunPlan() == Block.BLOCK_3_4 && module.isMandatory() != true) {
				unselectedList.add(module);
			}
		}

		block1.forEach(e -> block1ModulesList.add(e.toString()));
		block2.forEach(e -> block2ModulesList.add(e.toString()));
		unselectedList.forEach(e -> unselectedModuleList.add(e.toString()));
		selectedList.forEach(e -> selectedModuleList.add(e.toString()));

		block1.forEach(e -> block1Modules.put(e.toString(), e));
		block2.forEach(e -> block2Modules.put(e.toString(), e));
		unselectedList.forEach(e -> unselectedModules.put(e.toString(), e));
		selectedList.forEach(e -> selectedModules.put(e.toString(), e));

		smp.getblock1ModuleList().setItems(block1ModulesList);
		smp.getblock2ModuleList().setItems(block2ModulesList);
		smp.getUnselectedModuleList().setItems(unselectedModuleList);
		smp.getSelectedModuleList().setItems(selectedModuleList);

		updateCreditScoreView();

		osp.getStudentProfileTextBox().clear();
		osp.getSelectedModulesTextBox().clear();
		osp.getReservedModulesTextBox().clear();

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * SELECT MODULE PANE
	 */

	/**
	 * smp add btn handler
	 */
	private void smpAddBtnHandler() {
		if (smp.getUnselectedModuleList().getSelectionModel().getSelectedItem() != null) {
			if (Integer.parseInt(smp.getcreditsField().getText()) < 120) {

				var selectedIndex = smp.getUnselectedModuleList().getSelectionModel().getSelectedIndex();
				var selectedItem = unselectedModules.get(unselectedModuleList.get(selectedIndex));

				// adding to selected listview
				selectedModuleList.add(selectedItem.toString()); // adding to selected list
				selectedModules.put(selectedItem.toString(), selectedItem); // adding to selected map

				// removing from unselected listview
				unselectedModuleList.remove(selectedIndex); // removing from the unselected list
				unselectedModules.remove(selectedItem.toString()); // removing from the unselected map

				// updating credit scores
				updateCreditScoreView();

				// updating listviews
				smp.getUnselectedModuleList().setItems(unselectedModuleList); // updating unselected block 3/4
																				// listview
				smp.getSelectedModuleList().setItems(selectedModuleList); // updating selected block 3/4 listview
			} else {
				new AlertBox(AlertType.INFORMATION, "Info", "Total credit score limit reached.",
						"Maximum credits score for this academic year is 120 credits!");
			}
		} else {

			new AlertBox(AlertType.INFORMATION, "Info", "No modules are selected.",
					"At least one module must be selected in order to add from unselected modules list!");
		}
	}

	/**
	 * smp remove btn handler
	 */
	private void smpRemoveBtnHandler() {
		if (smp.getSelectedModuleList().getSelectionModel().getSelectedItem() != null) {

			var selectedIndex = smp.getSelectedModuleList().getSelectionModel().getSelectedIndex();
			var selectedItem = selectedModules.get(selectedModuleList.get(selectedIndex));

			if (!selectedItem.isMandatory()) {

				// cleaning the selected modules list from student profile model
				studentProfile.getAllSelectedModules().clear();

				// removing from selected listview
				selectedModuleList.remove(selectedIndex); // removing from the selected list
				selectedModules.remove(selectedItem.toString()); // removing from the selected map

				// adding back to unselected listview
				unselectedModuleList.add(selectedItem.toString()); // adding back to unselected list
				unselectedModules.put(selectedItem.toString(), selectedItem); // adding back to unselected map

				// updating credit scores
				updateCreditScoreView();

				// updating listview
				smp.getSelectedModuleList().setItems(selectedModuleList);
			} else {
				new AlertBox(AlertType.INFORMATION, "Info", "Mandatory module selected.",
						"Only optional modules can be removed!");
			}
		} else {

			new AlertBox(AlertType.INFORMATION, "Info", "No modules are selected.",
					"At least one module must be selected in order to remove  from selected modules list!");
		}

	}

	/**
	 * smp reset btn handler
	 */
	private void smpResetBtnHandler() {

		// validation case for avoiding redundant view state updating
		if (selectedModuleList.size() > 1) {
			getModulesInitialState(); // resets the smp state to initial state
		}
	}

	/**
	 * smp submit btn handler
	 */
	private void smpSubmitBtnHandler() {

		// validation - if only the optional module is selected
		if (currentCredits == 120) {

			// populating reserve module view unselected listview
			rmp.getUnselectedModulesListview().getItems().clear(); // resetting view state

			reservedUnselectedModulesList.addAll(unselectedModuleList);
			reservedUnselectedModules.putAll(unselectedModules);
			rmp.getUnselectedModulesListview().setItems(reservedUnselectedModulesList);

			// populating reserve module view reserved listview
			rmp.getReservedModulesListview().getItems().clear(); // resetting view state
			rmp.getReservedModulesListview().setItems(reservedModuleList);

			// navigation to reserve modules tab
			view.changeTab(2);
		} else {

			new AlertBox(AlertType.INFORMATION, "Info", "Credits points not enough (120 points required).",
					"Select optional modules (Unselected Block 3/4 modules) in order to make required 120 credits points to submit!");
		}
	}

	// helper methods

	/**
	 * helper method to update credit score text field
	 */
	private void updateCreditScoreView() {
		// updating current total credits points
		smp.getcreditsField().setText(getCurrentCredits());
	}

	/**
	 * helper method - calculates the current credits of the selected modules
	 */
	private String getCurrentCredits() {
		/**
		 * iterate block 1, block 2 and selected block modules for 3/4 and calculate the
		 * modules credit scores for eg: if there's total 4 modules then the total
		 * credits will be 4 x 30 = 120, if there is only 3 modules then it will be 90
		 * credits
		 */
		currentCredits = 0;

		selectedModules.forEach((key, value) -> {
			currentCredits += 30;
		});

		return Integer.toString(currentCredits += 60);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * RESERVE MODULE PANE
	 */

	/**
	 * rmp add btn handler
	 */
	private void rmpAddBtnHandler() {

		if (rmp.getUnselectedModulesListview().getSelectionModel().getSelectedItem() != null) {

			var selectedIndex = rmp.getUnselectedModulesListview().getSelectionModel().getSelectedIndex();
			var selectedItem = reservedUnselectedModules.get(reservedUnselectedModulesList.get(selectedIndex));

			// condition for swapping / adding reserved modules
			if (reservedModuleList.size() < 1) {

				// adding reserved module to the reserved list if there is no any modules
				// selected
				reservedModuleList.add(selectedItem.toString());
				reservedModules.put(selectedItem.toString(), selectedItem);

				reservedUnselectedModulesList.remove(selectedIndex); // removing from unselected modules list
				reservedUnselectedModules.remove(selectedItem.toString());

			} else {
				new AlertBox(AlertType.INFORMATION, "Info", "Reserved Modules Limit.",
						"Reserved module limit exceeded, only a module can be reserved!");
			}

			// updating the view
//			rmp.getUnselectedModulesListview().getItems().setAll(unselectedModuleList);
			rmp.getReservedModulesListview().setItems(reservedModuleList);
		} else {

			new AlertBox(AlertType.INFORMATION, "Info", "No modules are selected.",
					"At least one module must be selected from unselected modules list to add it to reserved list!");
		}
	}

	/**
	 * rmp remove btn handler
	 */
	private void rmpRemoveBtnHandler() {

		if (rmp.getReservedModulesListview().getSelectionModel().getSelectedItem() != null) {

			var selectedIndex = rmp.getReservedModulesListview().getSelectionModel().getSelectedIndex();
			var selectedItem = reservedModules.get(reservedModuleList.get(selectedIndex));

			reservedModuleList.remove(selectedIndex);
			reservedUnselectedModulesList.add(selectedItem.toString());

			reservedModules.remove(selectedItem.toString());
			reservedUnselectedModules.put(selectedItem.toString(), selectedItem);

			// updating the view
			rmp.getUnselectedModulesListview().setItems(reservedUnselectedModulesList);
			rmp.getReservedModulesListview().setItems(reservedModuleList);
		} else {

			new AlertBox(AlertType.INFORMATION, "Info", "No modules are selected.",
					"At least one module must be selected from reserved modules list to remove!");
		}
	}

	/**
	 * rmp confirm btn handler
	 */
	private void rmpConfirmBtnHandler() {
		if (!reservedModuleList.isEmpty()) {
			// extracting reserved module from reserved moules map
			final Module reservedModule = reservedModules.get(reservedModuleList.get(0));

			// updating student profile with all selected modules
			studentProfile.getAllSelectedModules().add(block1Modules.get(block1ModulesList.get(0)));
			studentProfile.getAllSelectedModules().add(block2Modules.get(block2ModulesList.get(0)));
			selectedModules.forEach((key, module) -> studentProfile.getAllSelectedModules().add(module));

			// update the reserved modules in student profile
			studentProfile.getAllReservedModules().clear();
			studentProfile.getAllReservedModules().add(reservedModule);

			// populating the overview section pane before navigating
			osp.getStudentProfileTextBox().setText(generateProfileData());
			osp.getSelectedModulesTextBox()
					.setText("*SELECTED MODULES*\n\n" + generateModulesData(studentProfile.getAllSelectedModules()));
			osp.getReservedModulesTextBox()
					.setText("*RESERVED MODULES*\n\n" + generateModulesData(studentProfile.getAllReservedModules()));

			// navigating to overview section pane
			view.changeTab(3);
		} else {
			new AlertBox(AlertType.INFORMATION, "Info", "No modules are reserved.",
					"At least one module must be reserved from unselected modules list!");
		}
	}

	// helper methods

	/**
	 * helper method to generate student profile date for overview
	 */
	private String generateProfileData() {
		final String profileData = "*STUDENT PROFILE*\n\nName: " + studentProfile.getStudentName().getFullName()
				+ "\nPNo.: " + studentProfile.getStudentPnumber() + "\nEmail: " + studentProfile.getStudentEmail()
				+ "\nDate: " + studentProfile.getSubmissionDate() + "\nCourse: "
				+ studentProfile.getStudentCourse().getCourseName();
		return profileData;
	}

	/**
	 * helper method to generate modules date for overview
	 */
	private String generateModulesData(Set<Module> modules) {
		var selectedModulesData = new ArrayList<String>();

		modules.forEach((module) -> {
			var data = "";

			final var mandatory = module.isMandatory() == true ? "Mandatory" : "Optional";

			data += "Module code: " + module.getModuleCode() + "\nModule name: " + module.getModuleName() + " ["
					+ mandatory + "]" + "\nCredits: " + module.getModuleCredits() + "\nBlock: "
					+ getBlockMetaData(module.getRunPlan());
			selectedModulesData.add(data);
		});
		return String.join("\n\n", selectedModulesData);
	}

	/**
	 * helper method to get block meta data coressponding appropriate string
	 * ("BLOCK_1" -> "Block 1")
	 */
	private String getBlockMetaData(Block block) {
		switch (block) {
		case BLOCK_1: {
			return "Block 1";
		}
		case BLOCK_2: {
			return "Block 2";
		}
		case BLOCK_3_4: {
			return "Block 3/4";
		}
		default:
			return "no data";
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * OVERVIEW SECTION PANE
	 */

	/**
	 * osp save btn handler
	 */
	private void ospSaveBtnHandler() {
		try {
			var out = new PrintWriter(new File("overview.txt"));

			out.println(osp.getStudentProfileTextBox().getText() + "\n");
			out.println(osp.getSelectedModulesTextBox().getText() + "\n");
			out.println(osp.getReservedModulesTextBox().getText());

			out.close();
			new AlertBox(AlertType.INFORMATION, "Info", "Student overview.",
					"Overview details saved in the file 'overview.txt'!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// helper methods for overview section

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * MENU SETION TAB MENU BAR
	 */

	/**
	 * mstmb save student profile menu item handler
	 */
	private void mstmbSaveProfileHandler() {
		if (!studentProfile.getAllSelectedModules().isEmpty() && !studentProfile.getAllReservedModules().isEmpty()
				&& !osp.getStudentProfileTextBox().getText().isEmpty()
				&& !osp.getSelectedModulesTextBox().getText().isEmpty()
				&& !osp.getReservedModulesTextBox().getText().isEmpty()) {

			ObjectOutputStream out = null;
			Storage storage = new Storage();

			// storing all the current state of the application into the storage model
			storage.setStudentProfile(studentProfile); // profile data

			// storing unselected modules list in storage
			unselectedModules.forEach((key, value) -> storage.getUnselectedModules().add(value));

			// storing unselected modules after reserving a module
			reservedUnselectedModules.forEach((key, value) -> {
				storage.getReservedUnselectedModules().add(value);
			});

			// overview section data
			storage.getOverviewList().add(osp.getStudentProfileTextBox().getText()); // profile overview data
			// selected modules overview data
			storage.getOverviewList().add(osp.getSelectedModulesTextBox().getText());
			// reserved modules overview data
			storage.getOverviewList().add(osp.getReservedModulesTextBox().getText());

			try {
				out = new ObjectOutputStream(new FileOutputStream("student_data.dat"));

				out.writeObject(storage);

				out.flush();

				new AlertBox(AlertType.INFORMATION, "Info", "Student Profile.",
						"Student profile data has been saved successfully!");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} else {
			new AlertBox(AlertType.WARNING, "Info", "Completion alert!",
					"Please complete your form in order to save your progress!");
		}
	}

	/**
	 * helper method for loading saved student data into application
	 */
	private void mstmbLoadStudentDataHandler() {

		ObjectInputStream in = null;

		try {
			// reading saved student model data from file //
			in = new ObjectInputStream(new FileInputStream("student_data.dat"));

			Storage storage = (Storage) in.readObject();

			in.close();

			// updating the saved data state in the application

			studentProfile = storage.getStudentProfile();

			// clearing the all modules list
			block1Modules.clear();
			block1ModulesList.clear();

			block2Modules.clear();
			block2ModulesList.clear();

			selectedModules.clear();
			selectedModuleList.clear();

			unselectedModules.clear();
			unselectedModuleList.clear();

			reservedUnselectedModules.clear();
			reservedUnselectedModulesList.clear();

			reservedModules.clear();
			reservedModuleList.clear();

			// for selected modules
			storage.getStudentProfile().getAllSelectedModules().forEach((e) -> {
				if (e.getRunPlan().toString().equalsIgnoreCase(Block.BLOCK_1.toString())) {
					block1ModulesList.add(e.toString());
					block1Modules.put(e.toString(), e);
				} else if (e.getRunPlan().toString().equalsIgnoreCase(Block.BLOCK_2.toString())) {
					block2ModulesList.add(e.toString());
					block2Modules.put(e.toString(), e);
				} else if (e.getRunPlan().toString().equalsIgnoreCase(Block.BLOCK_3_4.toString())) {
					selectedModuleList.add(e.toString());
					selectedModules.put(e.toString(), e);
				}
			});

			// for unselected modules (for unselected reserve modules as well)
			storage.getUnselectedModules().forEach((e) -> {
				unselectedModuleList.add(e.toString());
				unselectedModules.put(e.toString(), e);
			});

			// for unselected modules after reserved module
			storage.getReservedUnselectedModules().forEach((e) -> {
				reservedUnselectedModules.put(e.toString(), e);
				reservedUnselectedModulesList.add(e.toString());
			});

			// for reserved modules
			storage.getStudentProfile().getAllReservedModules().forEach((e) -> {
				reservedModuleList.add(e.toString());
				reservedModules.put(e.toString(), e);
			});

			// populating the data into view //

			// updating cssp view
			cspp.getSelectedCourse().setCourseName(storage.getStudentProfile().getStudentCourse().getCourseName());
			cspp.getTxtPnumber().setText(storage.getStudentProfile().getStudentPnumber());
			cspp.getTxtFirstName().setText(storage.getStudentProfile().getStudentName().getFirstName());
			cspp.getTxtSurname().setText(storage.getStudentProfile().getStudentName().getFamilyName());
			cspp.getTxtEmail().setText(storage.getStudentProfile().getStudentEmail());
			cspp.getInputDate().setValue(storage.getStudentProfile().getSubmissionDate());
			cspp.getInputDate().getEditor().setText(storage.getStudentProfile().getSubmissionDate()
					.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")).toString());

			// updating smp view
			smp.getblock1ModuleList().setItems(block1ModulesList);
			smp.getblock2ModuleList().setItems(block2ModulesList);
			smp.getSelectedModuleList().setItems(selectedModuleList);
			smp.getUnselectedModuleList().setItems(unselectedModuleList);

			smp.getcreditsField().setText(getCurrentCredits());

			// updating rmp view
			rmp.getUnselectedModulesListview().setItems(reservedUnselectedModulesList);
			rmp.getReservedModulesListview().setItems(reservedModuleList);

			// updating osp
			osp.getStudentProfileTextBox().setText(storage.getOverviewList().get(0));
			osp.getSelectedModulesTextBox().setText(storage.getOverviewList().get(1));
			osp.getReservedModulesTextBox().setText(storage.getOverviewList().get(2));

			new AlertBox(AlertType.INFORMATION, "Info", "Student Profile.",
					"Student profile data has been restored successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * mstmb save student profile menu item handler
	 */
	private void mstmbHelpMenuItemHandler() {
		new AlertBox(AlertType.INFORMATION, "About", "Module Chooser Application.",
				"This program is a simple implementation of Object Oriented Design GUI Specification in JavaFX application."
						+ "\n\nFeatures:" + "\n- Create student profile."
						+ "\n- Select optional and reserved modules along with mandatory modules."
						+ "\n- Generate student profile overview." + "\n- Save student profile overview into file."
						+ "\n- Save student profile data." + "\n- Retrive previously saved student profile data."
						+ "\n- Load course data from text file. " + "\n\nNote:"
						+ "\nThe courses data in the file should follow the standard input data schema as the line of string seperated by comma as shown below:"
						+ "\n'Course name, Module code, Module name, Module credits, Is mandatory, Run plan'.");
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * methods to generate courses and modules
	 */

	/**
	 * helper method - generates modules and course data and returns courses within
	 * an array
	 */
	private Course[] generateAndGetCourses() {

		Course compSci = new Course(COMPUTER_SCI);
		Course softEng = new Course(SOFTWARE_ENG);

		var data = getDataFromFile();

		for (String[] line : data) {
			String course = line[0];
			String moduleCode = line[1];
			String moduleName = line[2];
			Integer moduleCredits = Integer.parseInt(line[3]);
			Boolean isMandatory = Boolean.parseBoolean(line[4]);
			Block runPlan = convertToBlockType(line[5]);

			if (course.equalsIgnoreCase(COMPUTER_SCI)) {
				var module = new Module(moduleCode, moduleName, moduleCredits, isMandatory, runPlan);
				compSci.addModule(module);
			}

			if (course.equalsIgnoreCase(SOFTWARE_ENG)) {
				var module = new Module(moduleCode, moduleName, moduleCredits, isMandatory, runPlan);
				softEng.addModule(module);
			}

		}

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}

	/**
	 * helper method to process course text file data
	 */
	private ArrayList<String[]> getDataFromFile() {
		Scanner in = null;

		String line = "";
		var arr = new ArrayList<String[]>();

		try {
			in = new Scanner(new File("courses.txt"));

			while (in.hasNextLine()) {
				line = in.nextLine();

				if (!line.isEmpty()) {
					arr.add(line.split(", "));
				}
			}

			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return arr;
	}

	/**
	 * helper method to convert string run plan "Block 1" to Block type BLOCK_1
	 */
	private Block convertToBlockType(String str) {

		Block value = null;

		switch (str.toLowerCase()) {
		case "block 1": {
			value = Block.BLOCK_1;
			break;
		}
		case "block 2": {
			value = Block.BLOCK_2;
			break;
		}
		case "block 3/4": {
			value = Block.BLOCK_3_4;
			break;
		}
		}
		return value;
	}

}
