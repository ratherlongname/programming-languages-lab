parent(jatin, avantika).
parent(jolly, jatin).
parent(jolly, kattappa).
parent(manisha, avantika).
parent(manisha, shivkami).
parent(bahubali, shivkami).

male(kattappa).
male(jolly).
male(bahubali).

female(shivkami).
female(avantika).

uncle(X, Y) :-    % X is uncle of Y
    parent(A, Y),
    parent(B, A),
    parent(B, X),
    male(X).

halfsister(X, Y) :-    % X is half sister of Y
    parent(A, X),
    parent(A, Y),
    parent(B, X),
    parent(C, Y),
    not(A = B),
    not(A = C),
    not(B = C),
    female(X).