package com.status.videomaker.videolib.libffmpeg;

import java.io.IOException;

class ShellCommand {
    ShellCommand() {
    }

    public Process run(String[] strArr) {
        try {
            return Runtime.getRuntime().exec(strArr);
        } catch (IOException e) {
            return null;
        }
    }

    public CommandResult runWaitFor(String[] strArr) {
        String str;
        Integer num;
        Process run = run(strArr);
        Integer num2 = null;
        if (run != null) {
            try {
                num = Integer.valueOf(run.waitFor());
                if (CommandResult.success(num)) {
                    num2 = Integer.valueOf(Util.convertInputStreamToString(run.getInputStream()));
                } else {
                    num2 = Integer.valueOf(Util.convertInputStreamToString(run.getErrorStream()));
                }
            } catch (InterruptedException e2) {
                num = null;
                Util.destroyProcess(run);
                Integer num32 = num;
                str = String.valueOf(num2);
                num2 = num32;
                Util.destroyProcess(run);
                return new CommandResult(CommandResult.success(num2), str);
            }
            Util.destroyProcess(run);
            Integer num322 = num;
            str = String.valueOf(num2);
            num2 = num322;
        } else {
            str = null;
        }
        Util.destroyProcess(run);
        return new CommandResult(CommandResult.success(num2), str);
    }

}

