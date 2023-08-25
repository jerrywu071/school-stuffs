/*
Name: Jerry Wu
Student number: 217545898
Description: This program will create 5 philosopher threads
*/

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

#define NUM_PHILOSOPHERS 5
#define MAX_ALLOWED_EATING 4

//define a semaphore for the chopsticks as well as an eating limit
sem_t chopsticks[NUM_PHILOSOPHERS];
sem_t eating_limit;

//create a function to simulate each philosopher's actions
void *philosopher(void *arg)
{
    int id = *(int *)arg;

    while (1)
    {
        printf("Philosopher %d is in THINKING mode\n", id);

        //think for 5 seconds
        sleep(5);

        //make sure to limit the number of philosophers eating at the same time using sem_wait
        sem_wait(&eating_limit);

        //pick up chopsticks
        sem_wait(&chopsticks[id]);
        printf("Philosopher %d picked up left chopstick %d\n", id, id);

        int right_chopstick = (id + 1) % NUM_PHILOSOPHERS;
        sem_wait(&chopsticks[right_chopstick]);
        printf("Philosopher %d picked up right chopstick %d\n", id, right_chopstick);

        //eat for 5 seconds
        printf("Philosopher %d is in EATING mode\n", id);
        sleep(5);

        //put down chopsticks
        sem_post(&chopsticks[id]);
        sem_post(&chopsticks[right_chopstick]);

        sem_post(&eating_limit);

        printf("Philosopher %d is in RELAXING mode\n", id);

        //terminate after each philosopher finishes exactly one cycle
        sleep(5);
        break;
    }

    return NULL;
}

int main()
{
    pthread_t philosophers[NUM_PHILOSOPHERS];
    int philosopher_ids[NUM_PHILOSOPHERS];

    //initialize semaphores
    sem_init(&eating_limit, 0, MAX_ALLOWED_EATING);

    for (int i = 0; i < NUM_PHILOSOPHERS; ++i)
    {
        sem_init(&chopsticks[i], 0, 1);
    }

    //create philosopher threads
    for (int i = 0; i < NUM_PHILOSOPHERS; ++i)
    {
        philosopher_ids[i] = i;
        if (pthread_create(&philosophers[i], NULL, philosopher, &philosopher_ids[i]) != 0)
        {
            perror("Failed to create philosopher thread");
            exit(EXIT_FAILURE);
        }
    }

    //wait for philosopher threads to finish executing
    for (int i = 0; i < NUM_PHILOSOPHERS; ++i)
    {
        pthread_join(philosophers[i], NULL);
    }

    //destroy semaphores after execution
    sem_destroy(&eating_limit);

    for (int i = 0; i < NUM_PHILOSOPHERS; ++i)
    {
        sem_destroy(&chopsticks[i]);
    }

    return 0;
}