package com.paracelsoft.teamsport.api.dto;

import java.util.List;

public class SearchEventKendoDTO {
	
	private List<EventKendoDTO> fixtures;
	
	private List<EventKendoDTO> listMatchStats; // Thống kê result 3 trận gần nhất của 2 team
	
	private List<Integer> homeTeamStats; // Thống kê win-lose 5 trận gần nhất
	
	private List<Integer> opponentTeamStats; // Thống kê win-lose 5 trận gần nhất
	
	private Integer total;

	/**
	 * @return the fixtures
	 */
	public List<EventKendoDTO> getFixtures() {
		return fixtures;
	}

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param fixtures the fixtures to set
	 */
	public void setFixtures(List<EventKendoDTO> fixtures) {
		this.fixtures = fixtures;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return the listMatchStats
	 */
	public List<EventKendoDTO> getListMatchStats() {
		return listMatchStats;
	}

	/**
	 * @param listMatchStats the listMatchStats to set
	 */
	public void setListMatchStats(List<EventKendoDTO> listMatchStats) {
		this.listMatchStats = listMatchStats;
	}

	/**
	 * @return the homeTeamStats
	 */
	public List<Integer> getHomeTeamStats() {
		return homeTeamStats;
	}

	/**
	 * @param homeTeamStats the homeTeamStats to set
	 */
	public void setHomeTeamStats(List<Integer> homeTeamStats) {
		this.homeTeamStats = homeTeamStats;
	}

	/**
	 * @return the opponentTeamStats
	 */
	public List<Integer> getOpponentTeamStats() {
		return opponentTeamStats;
	}

	/**
	 * @param opponentTeamStats the opponentTeamStats to set
	 */
	public void setOpponentTeamStats(List<Integer> opponentTeamStats) {
		this.opponentTeamStats = opponentTeamStats;
	}
}
