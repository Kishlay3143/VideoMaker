package com.status.videomaker.videolib.libffmpeg;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

import kotlin.jvm.internal.LongCompanionObject;

class FFmpegExecuteAsyncTask extends AsyncTask<Void, String, CommandResult> {
    private final String[] cmd;
    private final FFmpegExecuteResponseHandler ffmpegExecuteResponseHandler;
    private final ShellCommand shellCommand;
    private final long timeout;
    private String output = "";
    private Process process;
    private long startTime;

    FFmpegExecuteAsyncTask(String[] strArr, long j, FFmpegExecuteResponseHandler fFmpegExecuteResponseHandler) {
        this.cmd = strArr;
        this.timeout = j;
        this.ffmpegExecuteResponseHandler = fFmpegExecuteResponseHandler;
        this.shellCommand = new ShellCommand();
    }

    public void onPreExecute() {
        this.startTime = System.currentTimeMillis();
        FFmpegExecuteResponseHandler fFmpegExecuteResponseHandler = this.ffmpegExecuteResponseHandler;
        if (fFmpegExecuteResponseHandler != null) {
            fFmpegExecuteResponseHandler.onStart();
        }
    }

    public CommandResult doInBackground(Void... voidArr) {
        CommandResult commandResult = null;
        try {
            Process run = this.shellCommand.run(this.cmd);
            this.process = run;
            if (run == null) {
                commandResult = CommandResult.getDummyFailureResponse();
            } else {
                checkAndUpdateProcess();
                commandResult = CommandResult.getOutputFromProcess(this.process);
                Util.destroyProcess(this.process);
            }
        } catch (TimeoutException e) {
            CommandResult commandResult2 = new CommandResult(false, e.getMessage());
            Util.destroyProcess(this.process);
            return commandResult2;
        } catch (Exception e2) {
            CommandResult.getDummyFailureResponse();
        } catch (Throwable unused) {
        }
        Util.destroyProcess(this.process);
        return commandResult;
    }

    public void onProgressUpdate(String... strArr) {
        FFmpegExecuteResponseHandler fFmpegExecuteResponseHandler;
        if (strArr != null && strArr[0] != null && (fFmpegExecuteResponseHandler = this.ffmpegExecuteResponseHandler) != null) {
            fFmpegExecuteResponseHandler.onProgress(strArr[0]);
        }
    }

    public void onPostExecute(CommandResult commandResult) {
        if (this.ffmpegExecuteResponseHandler != null) {
            this.output += commandResult.output;
            if (commandResult.success) {
                this.ffmpegExecuteResponseHandler.onSuccess(this.output);
            } else {
                this.ffmpegExecuteResponseHandler.onFailure(this.output);
            }
            this.ffmpegExecuteResponseHandler.onFinish();
        }
    }

    private void checkAndUpdateProcess() throws TimeoutException, InterruptedException {
        while (!Util.isProcessCompleted(this.process) && !Util.isProcessCompleted(this.process)) {
            if (this.timeout == LongCompanionObject.MAX_VALUE || System.currentTimeMillis() <= this.startTime + this.timeout) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.process.getErrorStream()));
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine != null) {
                            if (!isCancelled()) {
                                this.output += readLine + "\n";
                                publishProgress(new String[]{readLine});
                            } else {
                                return;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                throw new TimeoutException("FFmpeg timed out");
            }
        }
    }

    public boolean isProcessCompleted() {
        return Util.isProcessCompleted(this.process);
    }
}
