.PHONY: answer-01 clean list
.DEFAULT: list

list:
	@echo "make <target>"
	@echo "where target can be:"
	@echo "\t answer-01 \t Build and run SockMatchingRobot"
	@echo "\t list \t\t Show this list of all available targets"
	@echo "\t clean \t\t Clean all temporary files"

answer-01: answer-01/SockMatchingRobot.java
	javac $^
	cd answer-01 && java SockMatchingRobot

clean:
	-rm */*.class