package myagentproject;
import org.lightjason.agentspeak.agent.IAgent;
import org.lightjason.agentspeak.language.execution.IVariableBuilder;
import org.lightjason.agentspeak.language.instantiable.IInstantiable;
import org.lightjason.agentspeak.language.variable.CConstant;
import org.lightjason.agentspeak.language.variable.IVariable;
import javax.annotation.Nonnull;
import java.text.MessageFormat;
import java.util.stream.Stream;

/**
 * and Based on/credits to https://lightjason.org/tutorials/communication/
 */
final class CVariableBuilder implements IVariableBuilder
{

    /**
     * environment reference
     */

    private final CEnvironment m_environment;


    /**
     * constructor
     *
     * @param p_environment environment
     */
    public CVariableBuilder( final CEnvironment p_environment )
    {
        m_environment = p_environment;
    }


    public final Stream<IVariable<?>> apply( final IAgent<?> p_agent, final IInstantiable p_runningcontext )
    {
        return Stream.of(
                new CConstant<>( "MyName", p_agent.<MyAgent>raw().getID() )
        );
    }
}

