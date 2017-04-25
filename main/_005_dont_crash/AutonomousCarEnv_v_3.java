package _005_dont_crash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import ail.mas.DefaultEnvironment;
import ail.mas.DefaultEnvironmentwRandomness;
import ail.mas.MAS;
import ail.syntax.Action;
import ail.syntax.NumberTermImpl;
import ail.syntax.Predicate;
import ail.syntax.Unifier;
import ail.syntax.VarTerm;
import ail.util.AILexception;
import util.GridCell;
import util.Passenger;
import util.Util;

public class AutonomousCarEnv_v_3 extends DefaultEnvironmentwRandomness{


	// Information about the car
	// Initial position of the car
	private int carX = 0;
	private int carY = 0;
	//private int scanRadius = 1;

	// Information about the grid
	
	// Size Max of the grid
	private int minX = 0;
	private int minY = 0;
	
	private int maxX = 10;
	private int maxY = 10;
	
	// Information about each position
	Map <String, GridCell> gridCells = new HashMap<String, GridCell>();

	// Information about passengers
	private ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	private int maxInitPassengers = 0;
	private Passenger currentPassenger = null;
	
	
	@Override
	public void initialise() {
		
	}
	
	@Override
	public void setMAS(MAS m) {
		super.setMAS(m);

		initGridInformation();
		initPassengerList();
		initObstacles();
		
		/*
		passengers.add(new Passenger("north" , 0, 0, 0, 5) );
		passengers.add(new Passenger("south" , 0, 5, 0, 0) );
		passengers.add(new Passenger("east" , 0, 0, 5, 0) );
		passengers.add(new Passenger("west" , 5, 0, 0, 0) );

		passengers.add(new Passenger("north and east" , 0, 0, 5, 5) );
		passengers.add(new Passenger("north and west" , 5, 5, 0, 10) );
		
		passengers.add(new Passenger("south and east" , 5, 5, 0, 10) );
		passengers.add(new Passenger("south and west" , 5, 5, 0, 0) );
		*/
		
		passengers.add(new Passenger("test1" , 0, 2, 0, 8) );
		//passengers.add(new Passenger("test2" , 2, 0, 8, 0) );
		
		for (Passenger passenger : passengers) {
			System.err.println( 
					String.format("Passenger %s: PickUp(%d,%d) - Drop Off (%d,%d)", 
							passenger.getName(), 
							passenger.getPickUpX(), passenger.getPickUpY(), 
							passenger.getDropOffX(), passenger.getDropOffY())
					);
		}
	}
	
	private void initGridInformation() {
		
		for(int x = minX; x < maxX; x++) {
			for(int y = minY; y < maxY; y++) {	
				String cellName = GridCell.getIndex(x, y);
				gridCells.put(cellName, new GridCell(x, y, false) );
			}
		}
	}

	private void initPassengerList() {
		for(int i = 0; i < maxInitPassengers; i++) {
			
			int pickUpX = random_ints.nextInt(maxX);
			int pickUpY = random_ints.nextInt(maxY);

			int dropOffX = random_ints.nextInt(maxX);
			int dropOffY = random_ints.nextInt(maxY);
			
			passengers.add(new Passenger( "" + i , pickUpX, pickUpY, dropOffX, dropOffY) );
		}
	}

	private void initObstacles() {
		gridCells.get( GridCell.getIndex(0, 1) ).setHasObstacle(true);
		gridCells.get( GridCell.getIndex(0, 3) ).setHasObstacle(true);
		gridCells.get( GridCell.getIndex(0, 5) ).setHasObstacle(true);
		gridCells.get( GridCell.getIndex(0, 7) ).setHasObstacle(true);

		gridCells.get( GridCell.getIndex(1, 0) ).setHasObstacle(true);
		gridCells.get( GridCell.getIndex(5, 0) ).setHasObstacle(true);
		gridCells.get( GridCell.getIndex(3, 0) ).setHasObstacle(true);
		gridCells.get( GridCell.getIndex(7, 0) ).setHasObstacle(true);
	}
	
	private void moveCar(String agName, String direction) throws AILexception{

		// Don't move if direction is invalid
		int newCarX = carX;
		int newCarY = carY;

		switch(direction) {
			case "north":
				// Move up in the Y axis
				newCarY++;
				break;
			case "south":
				// Move down in the Y axis
				newCarY--;
				break;
			case "east":
				// Move right in the X axis
				newCarX++;
				break;
			case "west":
				// Move left in the X axis
				newCarX--;
				break;
			default:
		}
		
		//scanSurroundings(agName, newCarX, newCarY);
		
		/*
		GridCell currentGridCell = gridCells.get( GridCell.getIndex(newCarX, newCarY) );
		
		if ( currentGridCell.hasObstacle() ) {
			Predicate obstacle = new Predicate("obstacle");
			obstacle.addTerm(new NumberTermImpl(newCarX));
			obstacle.addTerm(new NumberTermImpl(newCarY));
			
			addPercept(agName, obstacle);
		}
		*/

		//addPositionPercepts(newCarX, newCarY);
		
		Predicate oldPosition = new Predicate("at");
		oldPosition.addTerm(new NumberTermImpl(carX));
		oldPosition.addTerm(new NumberTermImpl(carY));

		Predicate at = new Predicate("at");
		at.addTerm(new NumberTermImpl(newCarX));
		at.addTerm(new NumberTermImpl(newCarY));

		System.err.println( String.format("Moving %s from (%d,%d) to (%d,%d)", direction, carX, carY, newCarX, newCarY) );

		// Update position of the agent in the environment
		carX = newCarX;
		carY = newCarY;

		removePercept(agName, oldPosition); //remove old position
		addPercept(agName, at); //inform new position to the agent
	}
	
	private void scanSurroundings(String agName, int x, int y) {
		
		clearScanSurroundings(agName);
		
		for(int x1 = 0; x1 < maxX; x1++) {
			for(int y1 = 0; y1 < maxY; y1++) {
				
				GridCell currentGridCell = gridCells.get( GridCell.getIndex(x1, y1) );
		
				if(currentGridCell.hasObstacle()) {
					Predicate obstacle = new Predicate("obstacle");
					obstacle.addTerm(new NumberTermImpl(x1));
					obstacle.addTerm(new NumberTermImpl(y1));

					System.err.println( String.format("Obstacle in (%d,%d)", x1, y1) );
					
					addPercept(agName, obstacle);
	
				}
			}
		}
		/*
		
		
		GridCell currentGridCell;

		for(int scanX = x-scanRadius; scanX <= x+scanRadius; scanX++) {
			for(int scanY = y-scanRadius; scanY <= y-scanRadius; scanY++) {
				
				if (scanX >= minX && scanX <= maxX && scanY >= minY && scanY <= maxY){
					currentGridCell = gridCells.get( GridCell.getIndex(scanX, scanY) );
					
					if(currentGridCell.hasObstacle()) {
						Predicate obstacle = new Predicate("obstacle");
						obstacle.addTerm(new NumberTermImpl(scanX));
						obstacle.addTerm(new NumberTermImpl(scanY));

						System.err.println( String.format("Obstacle in (%d,%d)", scanX, scanY) );
						
						addPercept(agName, obstacle);
					}
				}
				else {
					//System.err.println( String.format("Enter the void (%d,%d)", scanX, scanY) );
				}
			}
		}
		*/
	}
	

	// Identifies agents' actions
	public Unifier executeAction(String agName, Action act) throws AILexception {	
		
		String actionName = act.getFunctor();

		if(actionName.equals("drive")) {
			
			moveCar(agName, act.getTerm(0).getFunctor());
		
		}
		else if(actionName.equals("init_gps")) {

			Predicate at = new Predicate("at");
			at.addTerm(new NumberTermImpl(carX));
			at.addTerm(new NumberTermImpl(carY));

			System.err.println("Initializing GPS");
			System.err.println( String.format("Agent %s is %s", agName, at) );

			addPercept(agName, at);

			scanSurroundings(agName, carX, carY);
		}
		else if(actionName.equals("get_ride")) {
			
			getRide(agName);
			
		}
		else if(actionName.equals("park")) {
			
			if( carX == currentPassenger.getPickUpX() && carY == currentPassenger.getPickUpY() )
			{
				System.err.println( String.format("Pick Up Passanger %s in (%d,%d)", currentPassenger.getName(), carX, carY) );
			}
			else if( carX == currentPassenger.getDropOffX() && carY == currentPassenger.getDropOffY() )
			{
				System.err.println( String.format("Drop Off Passanger %s in (%d,%d)", currentPassenger.getName(), carX, carY) );
			}

		}
		else if(actionName.equals("call_emergency")) {
			
			int x = Util.getIntTerm( act.getTerm(0) );
			int y = Util.getIntTerm( act.getTerm(1) );
			
			callEmergency(agName, x, y);
		}
		else if(actionName.equals("message")) {

			System.err.println( String.format("%s says %s", agName, act.getTerm(0)) );

		}
		else if(actionName.equals("honk")) {

			System.err.println("HONK");
		}

		return super.executeAction(agName, act);

	}

	// Actions
	
	private void getRide(String agName) {

		
		Predicate pickUpAll = new Predicate("pick_up");
		pickUpAll.addTerm(new VarTerm("X"));
		pickUpAll.addTerm(new VarTerm("Y"));

		Predicate dropOffAll = new Predicate("drop_off");
		dropOffAll.addTerm(new VarTerm("X"));
		dropOffAll.addTerm(new VarTerm("Y"));

		removeUnifiesPercept(agName, pickUpAll);
		removeUnifiesPercept(agName, dropOffAll);
		
		if(passengers.isEmpty())
		{
			Predicate noPossibleRide = new Predicate("no_possible_new_ride");
			addPercept(agName, noPossibleRide);
			
			//Predicate possibleNewRide = new Predicate("possible_new_ride");
			//removePercept(agName, possibleNewRide);
			
			System.err.println("No more rides!");
		}
		else {
			//System.err.println("new ride!");
			currentPassenger = passengers.get(0);
			passengers.remove(0);

			int pickUpX = currentPassenger.getPickUpX();
			int pickUpY = currentPassenger.getPickUpY();

			int dropOffX = currentPassenger.getDropOffX();
			int dropOffY = currentPassenger.getDropOffY();

			System.err.println("Pick up is in (" + pickUpX + "," + pickUpY + ")");
			System.err.println("Drop off is in (" + dropOffX + "," + dropOffY + ")");

			Predicate pickUp = new Predicate("pick_up");
			pickUp.addTerm(new NumberTermImpl(pickUpX));
			pickUp.addTerm(new NumberTermImpl(pickUpY));

			Predicate dropOff = new Predicate("drop_off");
			dropOff.addTerm(new NumberTermImpl(dropOffX));
			dropOff.addTerm(new NumberTermImpl(dropOffY));
			
			
			addPercept(agName, pickUp);
			addPercept(agName, dropOff);
		}
		
		Predicate rideInfo = new Predicate("ride_info");
		addPercept(agName, rideInfo);
		
	}

	private void callEmergency(String agName, int x, int y) {
		
		System.err.println( String.format("%s crashed in (%d,%d). Calling Emergency.", agName, x, y) );
		
		Predicate help = new Predicate("help");
		help.addTerm(new NumberTermImpl( x ));
		help.addTerm(new NumberTermImpl( y ));
		
		addPercept(agName, help);
		
	}
	
	// Add or Remove Perceptions
	private void clearScanSurroundings(String agName) {
		
		Predicate obstacle = new Predicate("obstacle");
		obstacle.addTerm(new VarTerm("X"));
		obstacle.addTerm(new VarTerm("Y"));
		
		removePercept(agName, obstacle);
		
	}

}