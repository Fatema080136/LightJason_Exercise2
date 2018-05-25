//belief about required cycles to eat (5) and think (10)
timetoeat(5).

// initial-goal
!prepare.


// initial plan (triggered by the initial goal)
// collect chopsticks and eat. This plan fails if required chopsticks are unavailable
+!prepare <-
    get/leftchopstick;
    get/rightchopstick;
    generic/print( "philosopher", MyName, "get his/her chopsticks" );
    !eat
.

// backup plan of plan !prepare: if !prepare plan fails, philosophers again
//try to collect chopsticks and eat by calling plan !prepare
-!prepare <-
    generic/print( "philosopher", MyName, "is starving" );
    !prepare
.

+!release <-
    release/leftchopstick;
    release/rightchopstick;
    generic/print( "philosopher", MyName, "is releasing his/her chopsticks" )
.

//need 5 cycles to complete eating once. When
+!eat : >>( timetoeat(S), generic/type/isnumeric (S) && S > 0 )
    <-
        F = S - 1;
        -timetoeat(S);
        +timetoeat(F);
        generic/print( "philosopher", MyName, "is eating", S );
        !eat

    : >>( timetoeat(S), generic/type/isnumeric (S) && S == 0 )
    <- !release;
       generic/print( "philosopher", MyName, "ate at least once");
       !think
.

//philosopher is thinking for 10 cycles and after that they will again try to eat
+!think <-
// complete this plan
generic/print( "philosopher", MyName, "is thinking")
.


