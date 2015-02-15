package net.leaobr.cm.execution;

import java.util.Map;

public interface IEnvironmentExecutor {
	public Object execute(ExecutionContext context, Map<String, Object> arguments) throws Exception;
}
