.PHONY: answer-01 answer-02 answer-03 help
.DEFAULT: help

help:
	@echo "make <target>"
	@echo "where target can be:"
	@echo "\t answer-01 \t Build and run FindingRelationship"
	@echo "\t answer-02 \t Build and run BusTravelPlanner"
	@echo "\t answer-03 \t Build and run PrisonerEscape"
	@echo "\t help \t\t Show this help having all available targets"

answer-01: answer-01/FindingRelationship.pl
	prolog $^

answer-02: answer-02/BusTravelPlanner.pl
	prolog $^

answer-03: answer-03/PrisonerEscape.pl
	prolog $^
