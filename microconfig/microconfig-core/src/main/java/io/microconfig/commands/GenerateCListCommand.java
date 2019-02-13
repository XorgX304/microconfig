package io.microconfig.commands;

import io.microconfig.environments.EnvironmentProvider;
import lombok.RequiredArgsConstructor;

import java.io.File;

import static io.microconfig.utils.FileUtils.write;

@RequiredArgsConstructor
public class GenerateCListCommand implements Command {
    private final File serviceDir;
    private final EnvironmentProvider environmentProvider;

    @Override
    public void execute(CommandContext context) {
        context.getComponentGroup().ifPresent(group -> doWrite(context.getEnv(), group));
    }

    private void doWrite(String env, String group) {
        File dir = new File(serviceDir, ".mgmt");
        File file = new File(dir, "mgmt.clist");

        write(file.toPath(),
                environmentProvider
                        .getByName(env)
                        .getComponentGroupByName(group)
                        .getComponentNames()
        );
    }
}