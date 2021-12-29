public class Field {
	private boolean explored;
	private boolean mined;
	private boolean marked;
	private String modifiedAppearance;

	public Field(boolean mined) {
		explored = false;
		this.mined = mined;
		marked = false;
		// This value can be used for displaying the field and is set by the
		// user of this class. If this value is not used by the user, the field
		// has a default appearance
		modifiedAppearance = null;
	}

	public void setAppearance(String appereance) {
		modifiedAppearance = appereance;
	}

	public String getAppearance() {
		if (modifiedAppearance != null) {
			return modifiedAppearance;
		}

		if (explored) {
			if (mined) {
				return "X";
			} else {
				return " ";
			}
		} else if (marked) {
			return "#";
		} else {
			return "_";
		}
	}

	public boolean isExplored() {
		// if the field is mined and marked, it should show that the field is mined and
		// delete the mark
		if (isMined()) {
			modifiedAppearance = null;
		}
		
		return explored;
	}

	public boolean isMined() {
		return mined;
	}

	public void mark() {
		marked = true;
	}

	public void unmark() {
		marked = false;
	}

	public void explore() {
		this.explored = true;
	}
}