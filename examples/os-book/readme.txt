./ch3/message-passing:	IPC via message passing: Indirect, asynchronous, bounded capacity
Channel.java		Abstract interface (send(), receive())
MessageQueue.java	Implementation of Channel<Date>
Test.java		Driver 

./ch3/rmi:		Remote Method Invocation (RPC in Java)
README			Instructions on how to use (e.g., rmiregistry &)
RemoteDateImpl.java	Implementation of RemoteDate, server (use "java RemoteDateImpl &")
RemoteDate.java		Abstract interface (getDate())
RMIClient.java		Client (try "java RMICLient" a few times, then kill rmiregistry, java RemoteDateImpl)

./ch3/shared-memory:	IPC via shared memory
BufferImpl.java		Implementation of Buffer (w/ in, out, count)
Buffer.java		Abstract interface (insert(), remove())
Test.java		Driver

./ch3/sockets:		IPC via sockets
DateClient.java		Client (try "java DateClient" a few times, then kill java DateServer)
DateServer.java		Server (launch "java DateServer &" before trying client)



./ch4/cancellation:	Demo of asynchronous vs. deferred cancellation
InterruptibleThread.java

./ch4/message_passing:	Message passing w/ > 1 thread
Channel.java		Abstract interface (send(), receive())
Consumer.java		Consumes messages (receive())
Factory.java		Driver
MessageQueue.java	Implementation of Channel<Date>
Producer.java		Produces messages (send())
SleepUtilities.java	Napping method (used everywhere)

./ch4/thread_libraries:	NOT ON TEST
Driver.java
thrd-posix.c

./ch4/thread_pools:	NOT ON TEST
TPExample.java		Demo of thread pools; I made lots of changes to test & time



./ch6/hardware:			Software abstractions for hardware support for atomicity
AtomicBooleanFactory.java	Driver for Java class with synchronized getAndSet() & swap()
GetAndSetFactory.java		Driver for HardwareData.getAndSet() test 
HardwareDataFactory.java	Driver for HardwareData.getAndSet() & swap()
HardwareData.java		Java class with unsynchronized getAndSet() & swap()
MutualExclusionUtilities.java	Critical & remainder sections (i.e., napping)
SleepUtilities.java		Napping method (used everywhere)
SwapFactory.java		Driver for HardwareData.swap() test
Worker1a.java			Thread definition for AtomicBooleanFactory
Worker1.java			Thread definition for GetAndSetFactory
Worker2.java			Thread definition for SwapFactory
Worker.java			Thread definition for HardwareDataFactory

./ch6/semaphores:		Home-grown Semaphores
MutualExclusionUtilities.java	Critical & remainder sections (i.e., napping)
SemaphoreFactory.java		Driver
Semaphore.java			Semaphore class definition (synchronized acquire(), release())
SleepUtilities.java		Napping method (used everywhere)
Worker.java			Thread definition for SemaphoreFactory

./ch6/semaphores/boundedbuffer:	Applying home-grown Semaphores to Bounded Buffer problem
BoundedBuffer.java		Implementation of Buffer
Buffer.java			Abstract interface (insert(), remove())
Consumer.java			Thread to consume (remove())  messages (Dates)
Factory.java			Driver
Producer.java			Thread to produce (insert()) messages (Dates)
Semaphore.java			Home-grown class definition (synchronized acquire(), release())
SleepUtilities.java		Napping method (used everywhere)

./ch6/semaphores/readwrite:	Applying home-grown Semaphores to Readers & Writers problem
Database.java			Implementation of ReadWriteLock (acquire & release methods)
Factory.java			Driver
Reader.java			Thread for reading database
ReadWriteLock.java		Abstract interface for {acquire|release}{Read|Write}Lock
Semaphore.java			Home-grown class definition
SleepUtilities.java		Napping method (used everywhere)
Writer.java			Thread for writing database

./ch6/java-synchronization/boundedbuffer:  Uses Java synchronized methods (no Semaphores)
BoundedBuffer.java		Implementation of Buffer with synchronized insert(), remove(), try / catch InterruptedException, notify(), wait() [only difference between this version  and ch6/semaphores/boundedbuffer]
Buffer.java			Abstract interface [same as ch6/semaphores]
Consumer.java			Thread for consuming (remove()) messages (Dates) [same as ch6/semaphores]
Factory.java			Driver [same as ch6/semaphores]
Producer.java			Thread for producing (insert()) messages (Dates) [same as ch6/semaphores]
SleepUtilities.java		Napping method (used everywhere)

./ch6/java-synchronization/readwrite:	Uses Java Semaphores class (java.util.concurrent.Semaphore)
Database.java			Implementation of ReadWriteLock with Java Semaphores, try/catch InterruptedException [only difference between this version and the one in ch6/semaphores/readwrite]
Factory.java			Driver [same as ch6/semaphores]
Reader.java			Thread for reading database [same as ch6/semaphores]
ReadWriteLock.java		Abstract interface [same as ch6/semaphores]
SleepUtilities.java		Napping method (used everywhere)
Writer.java			Thread for writing database [same as ch6/semaphores]

./ch6/java-synchronization/dowork:
DoWork.java			Thread that uses wait(), notify(), try/catch InterruptedException
SleepUtilities.java		Napping method (used everywhere)
TestIt.java			Driver
Worker.java			Thread for critical section testing
