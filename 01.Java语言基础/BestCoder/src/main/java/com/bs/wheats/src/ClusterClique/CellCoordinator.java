package com.bs.wheats.src.ClusterClique;
import java.util.concurrent.LinkedBlockingQueue;

public class CellCoordinator {
	private static CellCoordinator instance = new CellCoordinator();
	public LinkedBlockingQueue<FieldSet> FieldSetQueue=new LinkedBlockingQueue<FieldSet>();


	public static CellCoordinator GetInstance()
	{
		return instance;
	}
}
