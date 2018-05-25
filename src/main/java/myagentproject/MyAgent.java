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

import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;

import javax.annotation.Nonnull;
import java.util.ArrayList;


/**
 * agent with internal actions, on default
 * all methods are blacklisted, so a method
 * which should be an action must be annotated
 *
 * Based on/credits to https://lightjason.org/tutorials/agentspeak-in-fifteen-minutes/
 */
@IAgentAction
final class MyAgent extends IBaseAgent<MyAgent>
{
    /**
     * serial id
     */
    private static final long serialVersionUID = -5105593330267677627L;
    /**
     * environment reference
     */
    private final CEnvironment m_environment;
    private Integer m_id;
    private ArrayList<Integer> m_fork;


    /**
     * constructor of the agent
     * @param p_configuration agent configuration of the agent generator
     * @param p_environment environment reference
     */
    MyAgent( @Nonnull final IAgentConfiguration<MyAgent> p_configuration, @Nonnull final CEnvironment p_environment )
    {
        super( p_configuration );
        m_environment = p_environment;

    }

    void setID( Integer p_id )
    {
        m_id = p_id;
    }
    void setforklist( ArrayList<Integer> p_fork )
    {
        m_fork = p_fork;
    }

    public Integer getID()
    {
        return  m_id;
    }

    /**
     * internal object action, that calls
     * the environment method
     */
    @IAgentActionFilter
    @IAgentActionName( name = "get/leftchopstick" )
    private void getLeftChopstick()
    {
        m_environment.getFork( this, this.m_fork.get( 0 ) );
    }

    @IAgentActionFilter
    @IAgentActionName( name = "get/rightchopstick" )
    private void getRightChopstick()
    {
        m_environment.getFork( this, this.m_fork.get( 1 ) );
    }

    @IAgentActionFilter
    @IAgentActionName( name = "release/leftchopstick" )
    private void releaseLeftChopsticks( )
    {
        m_environment.releaseFork( this.m_fork.get( 0 ) );
    }

    @IAgentActionFilter
    @IAgentActionName( name = "release/rightchopstick" )
    private void releaseRightChopsticks( )
    {
        m_environment.releaseFork( this.m_fork.get( 1 ) );
    }

}
