/*
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason                                                #
 * # Copyright (c) 2015-16, LightJason (info@lightjason.org)                            #
 * # This program is free software: you can redistribute it and/or modify               #
 * # it under the terms of the GNU Lesser General Public License as                     #
 * # published by the Free Software Foundation, either version 3 of the                 #
 * # License, or (at your option) any later version.                                    #
 * #                                                                                    #
 * # This program is distributed in the hope that it will be useful,                    #
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of                     #
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                      #
 * # GNU Lesser General Public License for more details.                                #
 * #                                                                                    #
 * # You should have received a copy of the GNU Lesser General Public License           #
 * # along with this program. If not, see http://www.gnu.org/licenses/                  #
 * ######################################################################################
 * @endcond
 */

package myagentproject;


import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.stream.IntStream;

/**
 * Environment Class
 * Based on/credits to https://lightjason.org/tutorials/agentspeak-in-fifteen-minutes/
 * and Based on/credits to https://lightjason.org/tutorials/environment/
 */
final class CEnvironment
{
    /**
     * Global thread-safe array of chopsticks (a table with five chopsticks)
     */
    private final AtomicReferenceArray<Integer> m_chopsticks = new AtomicReferenceArray<>(5 );

    /**
     * map with agent-to-chopsticks mapping
     */
    private final ConcurrentHashMap<MyAgent, ArrayList<Integer>> m_agent = new ConcurrentHashMap<>();

    CEnvironment()
    {
        IntStream.range( 0, 5 )
                .forEach( i -> m_chopsticks.set(i, 1) );

    }

    /**
     * initialize agents
     *
     * @param p_agent agent
     */
    final void initialset( @Nonnull final MyAgent p_agent, final ArrayList<Integer> p_chopsticklist )
    {
        m_agent.put( p_agent, p_chopsticklist );
    }


    /**
     * if m_chopsticks[i] is 0, means chopstick i is already taken, but if m_chopsticks[i] is 1,
     * then chopstick i is can be taken.
     *
     * @param p_agent agent
     * @param p_value new value
     */
    final boolean getFork( @Nonnull final MyAgent p_agent, @Nonnull final Number p_value )
    {
        if ( m_chopsticks.compareAndSet( p_value.intValue(), 1, 0 ) ) return true;
        else
        {
            throw new RuntimeException( "position is not free" );
        }
    }

    /**
     * release chopsticks
     *
     * @param p_value new value
     */
    final void releaseFork( @Nonnull final Number p_value )
    {
        m_chopsticks.compareAndSet( p_value.intValue(), 0, 1 ) ;
    }

}
