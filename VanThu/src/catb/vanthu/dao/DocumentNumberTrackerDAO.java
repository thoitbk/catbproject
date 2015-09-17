package catb.vanthu.dao;

public interface DocumentNumberTrackerDAO {
	
	public Integer getDocumentNumber(Integer id);
	public void updateDocumentNumber(Integer n, Integer id);
}
