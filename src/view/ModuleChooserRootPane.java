package view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;

public class ModuleChooserRootPane extends BorderPane {

	private CreateStudentProfilePane cspp;
	private SelectModulePane smp;
	private ReserveModulePane rmp;
	private OverviewSectionPane osp;
	private ModuleChooserMenuBar mstmb;
	private TabPane tp;

	public ModuleChooserRootPane() {

		// create tab pane and disable tabs from being closed
		tp = new TabPane();
		tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		// create panes
		cspp = new CreateStudentProfilePane();
		smp = new SelectModulePane();
		rmp = new ReserveModulePane();
		osp = new OverviewSectionPane();

		// create tabs with panes added
		Tab t1 = new Tab("Create Profile", cspp);
		Tab t2 = new Tab("Select Modules", smp);
		Tab t3 = new Tab("Reserve Modules", rmp);
		Tab t4 = new Tab("Overview Section", osp);

		// add tabs to tab pane
		tp.getTabs().addAll(t1, t2, t3, t4);

		// create menu bar
		mstmb = new ModuleChooserMenuBar();

		// add menu bar and tab pane to this root pane
		this.setTop(mstmb);
		this.setCenter(tp);

	}

	// methods allowing sub-containers to be accessed by the controller.
	public CreateStudentProfilePane getCreateStudentProfilePane() {
		return cspp;
	}

	public SelectModulePane getSelectModulePane() {
		return smp;
	}

	public ReserveModulePane getReserveModulePane() {
		return rmp;
	}

	public OverviewSectionPane getOverviewSectionPane() {
		return osp;
	}

	public ModuleChooserMenuBar getModuleSelectionToolMenuBar() {
		return mstmb;
	}

	// method to allow the controller to change tabs
	public void changeTab(int index) {
		tp.getSelectionModel().select(index);
	}
}
