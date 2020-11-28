.PHONY: answer-01 answer-02 answer-03 help
.DEFAULT: help

help:
	@echo "make <target>"
	@echo "where target can be:"
	@echo "\t answer-01 \t Build and run set-operations"
	@echo "\t answer-02 \t Build and run fixtures.hs"
	@echo "\t answer-03 \t Build and run house-planner.hs"
	@echo "\t help \t\t Show this help having all available targets"

answer-01: answer-01/set-operations.hs
	ghci $^

answer-02: answer-02/fixtures.hs
	ghci $^

answer-03: answer-03/house-planner.hs
	ghci $^
