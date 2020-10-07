.PHONY: answer-01 answer-02 answer-03 clean help
.DEFAULT: help

help:
	@echo "make <target>"
	@echo "where target can be:"
	@echo "\t answer-01 \t Build and run SockMatchingRobot"
	@echo "\t answer-02 \t Build and run EvaluationSystem"
	@echo "\t answer-03 \t Build and run DifferentCalculator"
	@echo "\t help \t\t Show this help having all available targets"
	@echo "\t clean \t\t Clean all temporary files"

answer-01: answer-01/SockMatchingRobot.java
	javac $^
	cd answer-01 && java SockMatchingRobot

answer-02: answer-02/EvaluationSystem.java
	javac $^
	cd answer-02 && java EvaluationSystem

answer-03: answer-03/DifferentCalculator.java
	javac $^
	cd answer-03 && java DifferentCalculator

clean:
	-rm */*.class
