package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Storage implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6827833929287549462L;

	// fields
	private StudentProfile studentProfile;
	private ArrayList<Module> unselectedModules;
	private ArrayList<Module> reservedUnselectedModules;
	private ArrayList<String> overviewList;

	// constructors
	public Storage() {
		this.studentProfile = new StudentProfile();
		this.unselectedModules = new ArrayList<Module>();
		this.reservedUnselectedModules = new ArrayList<Module>();
		this.overviewList = new ArrayList<String>();
	}

	// methods

	/**
	 * @param studentProfile the studentProfile to set
	 */
	public void setStudentProfile(StudentProfile studentProfile) {
		this.studentProfile = studentProfile;
	}

	/**
	 * @return the reservedUnselectedModules
	 */
	public ArrayList<Module> getReservedUnselectedModules() {
		return reservedUnselectedModules;
	}

	/**
	 * @return the studentProfile
	 */
	public StudentProfile getStudentProfile() {
		return studentProfile;
	}

	/**
	 * @return the unselectedModules
	 */
	public ArrayList<Module> getUnselectedModules() {
		return unselectedModules;
	}

	/**
	 * @return the overviewList
	 */
	public ArrayList<String> getOverviewList() {
		return overviewList;
	}

	/**
	 * this methods returns the string representation of the Storage model
	 */
	@Override
	public String toString() {
		return "Storage [studentProfile=" + studentProfile + ", unselectedModules=" + unselectedModules
				+ ", overviewList=" + overviewList + "]";
	}

}
