% ------------------------------------------------
% PRISON GATES INFO ------------------------------
% ------------------------------------------------

% Gate connections and distances -----------------
weighted_edge(gate1,gate5,4).
weighted_edge(gate2,gate5,6).
weighted_edge(gate3,gate5,8).
weighted_edge(gate4,gate5,9).
weighted_edge(gate1,gate6,10).
weighted_edge(gate2,gate6,9).
weighted_edge(gate3,gate6,3).
weighted_edge(gate4,gate6,5).
weighted_edge(gate5,gate7,3).
weighted_edge(gate5,gate10,4).
weighted_edge(gate5,gate11,6).
weighted_edge(gate5,gate12,7).
weighted_edge(gate5,gate6,7).
weighted_edge(gate5,gate8,9).
weighted_edge(gate6,gate8,2).
weighted_edge(gate6,gate12,3).
weighted_edge(gate6,gate11,5).
weighted_edge(gate6,gate10,9).
weighted_edge(gate6,gate7,10).
weighted_edge(gate7,gate10,2).
weighted_edge(gate7,gate11,5).
weighted_edge(gate7,gate12,7).
weighted_edge(gate7,gate8,10).
weighted_edge(gate8,gate9,3).
weighted_edge(gate8,gate12,3).
weighted_edge(gate8,gate11,4).
weighted_edge(gate8,gate10,8).
weighted_edge(gate10,gate15,5).
weighted_edge(gate10,gate11,2).
weighted_edge(gate10,gate12,5).
weighted_edge(gate11,gate15,4).
weighted_edge(gate11,gate13,5).
weighted_edge(gate11,gate12,4).
weighted_edge(gate12,gate13,7).
weighted_edge(gate12,gate14,8).
weighted_edge(gate15,gate13,3).
weighted_edge(gate13,gate14,4).
weighted_edge(gate14,gate17,5).
weighted_edge(gate14,gate18,4).
weighted_edge(gate17,gate18,8).

% Starting gates ---------------------------------
start_gate(gate1).
start_gate(gate2).
start_gate(gate3).
start_gate(gate4).

% Only one escape gate ---------------------------
escape_gate(gate17).


% ------------------------------------------------
% HELPER PREDICATES ------------------------------
% ------------------------------------------------

% Undirected edge between gates ------------------
edge(G1, G2):- weighted_edge(G1, G2, _).
edge(G1, G2):- weighted_edge(G2, G1, _).


% Check if list is empty -------------------------
list_empty([]).


% Print path that is list of gates ---------------
recur_write_path(Path):-
   [Head|Tail] = Path,    % If last gate of path
	list_empty(Tail),
	write(Head),    % Print last gate and enter
	nl.

recur_write_path(Path):-
   [G1,G2|_] = Path,    % Else if not last gate of path
   edge(G1, G2),
   write(G1),    % Print gate
   write(" -> "),    % Print arrow
   [_|Tail] = Path,    % recurse
	recur_write_path(Tail).


% Check validity of tail of path -----------------
valid_path_tail(P_tail):-    % If on last gate then last gate must be escape gate
	[Head|Tail] = P_tail,
	list_empty(Tail),
	escape_gate(Head).

valid_path_tail(P_tail):-    % Else if not on last gate then first, second gate must be connected, and recurse
	[G1,G2|_] = P_tail,
	edge(G1,G2),
	[_|Tail] = P_tail,
	valid_path_tail(Tail).


% Recurse to find all possible escape paths -----------------
:-dynamic(gate_visit/1).    % Predicate to dynamically mark gates visited

recur_paths(Gate, P_current):-
	escape_gate(Gate),    % If gate is escape gate, must end path and print it
	append(P_current, [Gate], P_updated),    % Add gate to path
	recur_write_path(P_updated),
	fail.    % Must fail to backtrack without user input

recur_paths(Gate, P_current):-    % Else if gate is not escape gate
	append(P_current, [Gate], P_updated),    % Add gate to path
	edge(Gate, Gate_neighbour),    % For each neighbour of gate
	\+ gate_visit(Gate_neighbour),    % If visited then backtrack
	asserta(gate_visit(Gate_neighbour)),    % Else, mark it visited
	\+ recur_paths(Gate_neighbour, P_updated),    % Recurse
	retract(gate_visit(Gate_neighbour)),    % Unmark visited
	fail.    % Must fail to backtrack without user input


% -------------------------------------------------
% USER FUNCTIONS ----------------------------------
% -------------------------------------------------

% ANSWER (A)
% Print all valid escape paths --------------------
get_all_paths:-
	start_gate(Gate),    % For each start gate
	asserta(gate_visit(Gate)),    % Mark gate visited (so that we don't get stuck in cycle)
	\+ recur_paths(Gate,[]),    % Recurse
	retract(gate_visit(Gate)),    % Unmark gate
	fail.    % Because we return fail in end, it backtracks to find all paths itself
            % Otherwise we would have to press ';' every time to get next possible path

% ANSWER (C)
% Check validity of escape path -------------------
valid(Path):-
	[Head|_] = Path,
	start_gate(Head),    % If first gate is start gate
	valid_path_tail(Path).    % and path's tail is also valid