import java.util.ArrayList;
import java.util.Vector;

/**
 * Scheduler 2: Acts as a multi-level feedback CPU scheduler of threads.
 * Note: rename file to 'Scheduler.java' before compiling.  Also, methods
 * previously provided by Scheduler_0.java that have not been modified have not
 * had their documentation changed.
 * 
 * @author Garrick Solberg
 *
 */
public class Scheduler extends Thread
{  
    private ArrayList<Vector<TCB>> queues; // Container of thread queues
    private int timeSlice; // nominal time quantum
    private static final int DEFAULT_TIME_SLICE = 1000;
    private static final int QUEUE_COUNT = 3; // Number of queue levels

    // New data added to p161 
    private boolean[] tids; // Indicate which ids have been used
    private static final int DEFAULT_MAX_THREADS = 10000;

    // A new feature added to p161 
    // Allocate an ID array, each element indicating if that id has been used
    private int nextId = 0;
    private void initTid( int maxThreads ) {
    	tids = new boolean[maxThreads];
    	for ( int i = 0; i < maxThreads; i++ )
    		tids[i] = false;
    }// end initTid(int)

    // A new feature added to p161 
    // Search an available thread ID and provide a new thread with this ID
    private int getNewTid( ) {
    	for ( int i = 0; i < tids.length; i++ ) {
    		int tentative = ( nextId + i ) % tids.length;
    		if ( tids[tentative] == false ) {
    			tids[tentative] = true;
    			nextId = ( tentative + 1 ) % tids.length;
    			return tentative;
    		}// end if
    	}// end for
    	return -1;
    }// end getNewTid()

    // A new feature added to p161 
    // Return the thread ID and set the corresponding tids element to be unused
    private boolean returnTid( int tid ) {
    	if ( tid >= 0 && tid < tids.length && tids[tid] == true ) {
    		tids[tid] = false;
    		return true;
    	}// end if
    	return false;
    }// end returnTid(int)

    // A new feature added to p161 
    // Retrieve the current thread's TCB from the queue
    public TCB getMyTcb( ) {
    	Thread myThread = Thread.currentThread( ); // Get my thread object

    	for ( int j=0; j < QUEUE_COUNT; j++ ) { // Iterate through all queues
    			Vector<TCB> queue = queues.get(j);  
    			synchronized( queue ) {
    				for ( int i = 0; i < queue.size( ); i++ ) {
    					TCB tcb = ( TCB )queue.elementAt( i );
    					Thread thread = tcb.getThread( );
    					if ( thread == myThread ) // if this is my TCB, return it
    						return tcb;
    				}// end for
    			}// end synchronized
    	}// end for   	
    	return null;
    }// end getMyTcb()

    // A new feature added to p161 
    // Return the maximal number of threads to be spawned in the system
    public int getMaxThreads( ) {
    	return tids.length;
    }

    /**
     * Default constructor.  Initializes queues, thread id container,
     * and sets nominal time quantum to default
     */
    public Scheduler( ) {
    	timeSlice = DEFAULT_TIME_SLICE;
    	queues = new ArrayList<Vector<TCB>>();
    	for ( int i=0; i < QUEUE_COUNT; i++ )
    		queues.add(new Vector<TCB>());
    	initTid( DEFAULT_MAX_THREADS );
    }// end constructor

    /**
     * Constructor.  Initializes queues, thread id container,
     * and sets nominal time quantum to specified parameter
     *
     * @param quantum nominal time quantum a thread will be given CPU processing
     * time, in milliseconds
     */
    public Scheduler( int quantum ) {
    	timeSlice = quantum;
    	queues = new ArrayList<Vector<TCB>>();
    	for ( int i=0; i < QUEUE_COUNT; i++ )
    		queues.add(new Vector<TCB>());
    	initTid( DEFAULT_MAX_THREADS );
    }// end constructor

    /**
     * Constructor.  Initializes queues, sets thread id container to size
     * specified, and sets nominal time quantum to specified parameter
     *
     * @param quantum nominal time quantum a thread will be given CPU processing
     * time, in milliseconds
     * @param maxThreads max number of threads to be spawned
     */
    public Scheduler( int quantum, int maxThreads ) {
    	timeSlice = quantum;
    	queues = new ArrayList<Vector<TCB>>();
    	for ( int i=0; i < QUEUE_COUNT; i++ )
    		queues.add(new Vector<TCB>());
    	initTid( maxThreads );
    }// end constructor
    
    /**
     * schedulerSleep: sleeps a thread for the time specified by parameter
     * 
     * @param sleepTime time in milliseconds that thread will sleep
     */
    private void schedulerSleep( int sleepTime )
	{
    	try {
    		Thread.sleep( sleepTime );
    	} catch ( InterruptedException e ) { e.printStackTrace(); }
    }// end schedulerSleep(int)

    /**
     * schedulerSleep: sleeps a thread
     */
    private void schedulerSleep( )
	{
    	try {
    		Thread.sleep( timeSlice );
    	} catch ( InterruptedException e ) { e.printStackTrace(); }
    }// end schedulerSleep()

    /**
     * addThread: a modified addThread of p161 example.  This method was modified
     * to put new threads into the highest priority queue.
     *
     * @param t thread to be added
     * @return TCB of the thread added to queue
     */
    public TCB addThread( Thread t ) {
	TCB parentTcb = getMyTcb( ); // get my TCB and find my TID
	int pid = ( parentTcb != null ) ? parentTcb.getTid( ) : -1;
	int tid = getNewTid( ); // get a new TID
	if ( tid == -1)
		return null;
	TCB tcb = new TCB( t, tid, pid ); // create a new TCB
	queues.get(0).addElement( tcb );  // always add to first queue
	return tcb;
    }// end addThread(Thread)

    // A new feature added to p161
    // Removing the TCB of a terminating thread
    public boolean deleteThread( ) {
    	TCB tcb = getMyTcb( ); 
    	if ( tcb!= null )
    		return tcb.setTerminated( );
    	else
    		return false;
    }

    public void sleepThread( int milliseconds ) {
    	try {
    		sleep( milliseconds );
    	} catch ( InterruptedException e ) { e.printStackTrace(); }
    }// end sleepThread(int)
    
    /**
     * run: a modified run of p161.  Method checks for active threads in every
     * queue, starting with Q0 and moving up.  When a non-empty queue is found,
     * the first thread in that queue is executed for the time quantum
     * appropriate for that queue.  If a thread has not completed during its
     * time quantum, the thread is moved to a lower priority queue, or to the
     * back of the queue if the thread is already in the lowest priority queue.
     * In addition, lower priority queues will check the highest priority queue
     * every 500ms to see if a new thread has been added.  If true, processing
     * will be given to the new thread and the current thread will be moved.
     *
     */
    @SuppressWarnings("deprecation")
	public void run( ) {
    	Thread current = null;
    	int sleepTime;
    	int queueNum;
	
    	while ( true ) {
    		try {
    			queueNum = 3;
    			for ( int i = 0; i < QUEUE_COUNT; i++ ) {
    				if ( queues.get(i).size() > 0 )
    					if ( i < queueNum )
    						queueNum = i;
    			}// end for
    			// Check if all queues empty, bypass loop if true
    			if ( queueNum >= QUEUE_COUNT )
    				continue;
    			
    			Vector<TCB> queue = queues.get(queueNum);
    			sleepTime = getQueueTimeSlice(queueNum);
    				
    			TCB currentTCB = (TCB)queue.firstElement( );
    			if ( currentTCB.getTerminated( ) == true ) {
    				queue.remove( currentTCB );
    				returnTid( currentTCB.getTid( ) );
    				continue;
    			}// end if
    			current = currentTCB.getThread( );
    			if ( current != null ) {
    				if ( current.isAlive( ) )
    					current.resume( );
    				else {
    					// Spawn must be controlled by Scheduler
    					// Scheduler must start a new thread
    					current.start( ); 			
    				}// end if/else
    			}// end if
		
    			if ( queueNum == 0 ) // Thread is in highest priority queue
    			{
    				schedulerSleep( sleepTime );
    			} else {
    				// Continue running thread in intervals until its time slice
    				// is met or a thread arrives in the highest priority queue
    				int timeSlept = 0;
    				do 
    				{
    					schedulerSleep( getQueueTimeSlice(0) );
    					timeSlept += getQueueTimeSlice(0);
    				} while ( timeSlept < sleepTime && queues.get(0).size() == 0 );
    			}// end else
    			

    			synchronized ( queue ) {
    				if ( current != null && current.isAlive( ) )
    					current.suspend( );
    				moveThread(queueNum, currentTCB);
    			}// end synchronized
    		} catch ( NullPointerException e3 ) { e3.printStackTrace(); };
    	}// end while
    }// end run()

    /**
     * getQueueTimeSlice: returns a time quantum based on the queue number
     * specified by the parameter
     *
     * @param queueNum number of the queue
     * @return time quantum for queue, in milliseconds
     */
    private int getQueueTimeSlice(int queueNum) {
    	
    	if ( queueNum == 0 )
    		return timeSlice / 2;
    	if ( queueNum == 1 )
    		return timeSlice;
    	if ( queueNum == 2 )
    		return timeSlice * 2;
    	return timeSlice; // invalid queue number given
    }// end getQueueTimeSlice(int)
    

    /**
     * moveThread: If parameter thread is in lowest priority queue, it will be 
     * moved to the end of the queue.  If thread is in a different queue, it 
     * will be moved to a lower priority queue.
     *
     * @param queueNum number of queue where thread exists
     * @param currentTCB thread to be moved
     */
    private void moveThread( int queueNum, TCB currentTCB)
    {
    	// Make sure valid queue number is given
    	if ( queueNum >= QUEUE_COUNT )
    		return;
    	
    	if ( queueNum < QUEUE_COUNT - 1 ) // Thread is not in last queue
    	{
    		// Remove thread from its current queue, move to lower priority queue
    		queues.get(queueNum).remove(currentTCB);
    		queues.get(queueNum + 1).add(currentTCB);
    	} else { // Thread is in the last queue, rotate to end of queue
    		queues.get(queueNum).remove(currentTCB);
    		queues.get(queueNum).add(currentTCB);
    	}// end if/else   		
    }// end moveThread(int, TCB)
    
}// end class
