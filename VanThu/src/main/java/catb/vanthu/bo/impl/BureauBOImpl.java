package catb.vanthu.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import catb.vanthu.bo.BureauBO;
import catb.vanthu.dao.BureauDAO;
import catb.vanthu.model.Bureau;

@Service
public class BureauBOImpl implements BureauBO {
	
	@Autowired
	private BureauDAO bureauDAO;
	
	@Override
	public List<Bureau> getBureaus() {
		return bureauDAO.getBureaus();
	}
}
