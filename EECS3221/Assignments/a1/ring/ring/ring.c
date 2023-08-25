/*Name: Jerry Wu
  Date: due June 12
  Description: creates a ring of N processes that send a signal round and round cnt times.
  to run the program, use the command: 
  ./ring N leader cnt
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/time.h>

int leaderPid;
int roundCount;
int processCount;

/*function to propagate process signals in a ring and output the result of the runtime*/
void sigusr1Handler(int signum)
{
    if (leaderPid == getpid())
    {
        static int round = 0;
        static struct timeval startTime;

        if (round == 0)
        {
            gettimeofday(&startTime, NULL);
        }

        round++;

        if (round == roundCount)
        {
            struct timeval endTime;
            gettimeofday(&endTime, NULL);

            //keep track of how much time elapsed since process was created
            double elapsedTime = (endTime.tv_sec - startTime.tv_sec) + (endTime.tv_usec - startTime.tv_usec) / 1000000.0;

            printf("%d %7.3e\n", roundCount, elapsedTime);

            //send sigterm to the leader process
            kill(leaderPid, SIGTERM);
        }
        else
        {
            //send sigusr1 to the next process in the ring
            kill(leaderPid, SIGUSR1);
        }
    }
    else
    {
        //send sigusr1 to the next process in the ring
        kill(leaderPid, SIGUSR1);
    }
}


/*make sure to exit when sigterm is invoked*/
void sigtermHandler(int signum)
{
    exit(0);
}

/*main function*/
int main(int argc, char *argv[])
{
    //make sure there are 4 arguments including the name of the program
    if (argc != 4)
    {
        fprintf(stderr, "Usage: %s N leader cnt\n", argv[0]);
        return 1;
    }

    //arguments
    processCount = atoi(argv[1]);
    leaderPid = atoi(argv[2]);
    roundCount = atoi(argv[3]);

    //signal handlers for sigusr1
    struct sigaction sa;
    sa.sa_handler = sigusr1Handler;
    sigemptyset(&sa.sa_mask);
    sa.sa_flags = 0;
    sigaction(SIGUSR1, &sa, NULL);

    //signal handlers for sigterm
    struct sigaction saTerm;
    saTerm.sa_handler = sigtermHandler;
    sigemptyset(&saTerm.sa_mask);
    saTerm.sa_flags = 0;
    sigaction(SIGTERM, &saTerm, NULL);

    if (leaderPid == 0)
    {
        //this process is the leader
        leaderPid = getpid();
    }

    if (processCount == 1)
    {
        //last process in the ring, start signaling phase
        kill(leaderPid, SIGUSR1);
    }
    else
    {
        //fork a child process and continue the ring
        char processCountStr[10];
        char leaderPidStr[10];
        char roundCountStr[10];
        sprintf(processCountStr, "%d", processCount - 1);
        sprintf(leaderPidStr, "%d", leaderPid);
        sprintf(roundCountStr, "%d", roundCount);

        char *args[] = {argv[0], processCountStr, leaderPidStr, roundCountStr, NULL};
        execv(argv[0], args);

        fprintf(stderr, "Error: Failed to create child process.\n");
        return 1;
    }

    //wait for SIGTERM to terminate
    while (1)
    {
        sleep(1);
    }

    return 0;
}