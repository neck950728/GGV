package member.other;

import java.util.List;

import member.bean.HelpCenterDTO;

public class HelpCenterListWrapper {
	private HelpCenterPaging paging;
	private List<HelpCenterDTO> helpCenterList;
	
	public HelpCenterPaging getPaging() {
		return paging;
	}
	
	public List<HelpCenterDTO> getHelpCenterList() {
		return helpCenterList;
	}
	
	public void setPaging(HelpCenterPaging paging) {
		this.paging = paging;
	}
	
	public void setHelpCenterList(List<HelpCenterDTO> helpCenterList) {
		this.helpCenterList = helpCenterList;
	}
}