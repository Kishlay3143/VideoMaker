package com.status.videomaker.videolib.libffmpeg;

class CommandResult {
    final String output;
    final boolean success;

    CommandResult(boolean z, String str) {
        this.success = z;
        this.output = str;
    }

    static CommandResult getDummyFailureResponse() {
        return new CommandResult(false, "");
    }

    static CommandResult getOutputFromProcess(Process process) {
        String str;
        if (success(Integer.valueOf(process.exitValue()))) {
            str = Util.convertInputStreamToString(process.getInputStream());
        } else {
            str = Util.convertInputStreamToString(process.getErrorStream());
        }
        return new CommandResult(success(Integer.valueOf(process.exitValue())), str);
    }

    static boolean success(Integer num) {
        return num != null && num.intValue() == 0;
    }
}
