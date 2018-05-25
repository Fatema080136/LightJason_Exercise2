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

import org.lightjason.agentspeak.common.CCommon;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * agent generator, which push the environment reference to the agent
 * Based on/credits to https://lightjason.org/tutorials/agentspeak-in-fifteen-minutes/
 * and Based on/credits to https://lightjason.org/tutorials/environment/
 */
final class MyAgentGenerator extends IBaseAgentGenerator<MyAgent>
{

    /**
     * environment reference
     */
    private final CEnvironment m_environment;
    private ConcurrentMap<Integer,ArrayList<Integer>> m_chopstickslistToAgentMapping = new ConcurrentHashMap<>();
    private final AtomicLong m_counter = new AtomicLong();


    /**
     * constructor of the generator
     *
     * @param p_stream asl stream
     * @param p_environment environment reference
     * @throws Exception on any error
     */
    MyAgentGenerator( @Nonnull final InputStream p_stream, @Nonnull final CEnvironment p_environment ) throws Exception
    {
        super(
            // input ASL stream
            p_stream,

            Stream.concat(

                // we use all built-in actions of LightJason
                CCommon.actionsFromPackage(),

                // read object actions, so that the agent get access to the environment
                CCommon.actionsFromAgentClass( MyAgent.class )

            ).collect( Collectors.toSet() ),

            // variable builder
            new CVariableBuilder( p_environment )
        );

        m_environment = p_environment;

        //make fork list for each agent
        ArrayList<Integer> m_chopstickslist0 = new ArrayList<>();
        m_chopstickslist0.add( 4 );
        m_chopstickslist0.add( 0 );
        ArrayList<Integer> m_chopstickslist1 = new ArrayList<>();
        m_chopstickslist1.add( 0 );
        m_chopstickslist1.add( 1 );
        ArrayList<Integer> m_chopstickslist2 = new ArrayList<>();
        m_chopstickslist2.add( 1 );
        m_chopstickslist2.add( 2 );
        ArrayList<Integer> m_chopstickslist3 = new ArrayList<>();
        m_chopstickslist3.add( 2 );
        m_chopstickslist3.add( 3 );
        ArrayList<Integer> m_forklist4 = new ArrayList<>();
        m_forklist4.add( 3 );
        m_forklist4.add( 4 );
        m_chopstickslistToAgentMapping.put( 0, m_chopstickslist0 );
        m_chopstickslistToAgentMapping.put( 1, m_chopstickslist1 );
        m_chopstickslistToAgentMapping.put( 2, m_chopstickslist2 );
        m_chopstickslistToAgentMapping.put( 3, m_chopstickslist3 );
        m_chopstickslistToAgentMapping.put( 4, m_forklist4 );

    }

    /**
     * generator method of the agent, which puts the
     * agent on a random position into the environment
     *
     * @param p_data any data which can be put from outside to the generator method
     * @return returns an agent
     */
    @Nullable
    @Override
    public final MyAgent generatesingle( @Nullable final Object... p_data )
    {
        Integer l_id = Math.toIntExact(m_counter.getAndIncrement());
        ArrayList<Integer> l_forklist = m_chopstickslistToAgentMapping.get( l_id );

        // create agent with a reference to the environment
        final MyAgent l_agent = new MyAgent( m_configuration, m_environment );

        //initialize agent
        l_agent.setID( l_id );
        l_agent.setforklist( l_forklist );
        m_environment.initialset( l_agent, l_forklist );
        //System.out.println( " philosopher " +l_agent.getID() + " left chopstick number " + l_agent.getforklist().get( 0 )
                //+ " right chopstick number " + l_agent.getforklist().get( 1 ) );
        return l_agent;
    }

}
