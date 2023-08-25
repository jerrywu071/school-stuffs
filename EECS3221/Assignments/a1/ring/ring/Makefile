NAME = ring

${NAME}: ${NAME}.c
	gcc ${NAME}.c -o ${NAME}

# Use the following target to check if there are zombie or other processes
# still hanging around after the execution
check:
	ps aux | grep $${USER} | grep ${NAME}

# Use the following target to clean up processes still persisting
clean:
	killall ${NAME}

# Use the following target to remove unneeded files
cleanup:
	/bin/rm ring

# Use the following target to do a run of your code
timing:
	./${NAME}_time.sh

# Use the following target to package up your code as a tar file.
tar:
	tar -cf ${NAME}.tar Makefile ${NAME}.c ${NAME}_time.sh timing.out
