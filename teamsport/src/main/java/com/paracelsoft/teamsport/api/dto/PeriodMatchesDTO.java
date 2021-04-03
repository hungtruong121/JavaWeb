package com.paracelsoft.teamsport.api.dto;

public class PeriodMatchesDTO {

	private String period;

	private int matches;

	private int win;

	private int draw;

	private int lose;

	public String getPeriod() {
		return period;
	}

	public int getMatches() {
		return matches;
	}

	public int getWin() {
		return win;
	}

	public int getDraw() {
		return draw;
	}

	public int getLose() {
		return lose;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public void setMatches(int matches) {
		this.matches = matches;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}
}
