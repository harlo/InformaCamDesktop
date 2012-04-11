package org.witness.informa.utils;

public interface Informa extends Constants {
	public void extractMetadata();
	public void extractMetadata(String md);
	public int getIntegrityRating();
	public void createClone();
}
