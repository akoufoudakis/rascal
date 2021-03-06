package org.rascalmpl.shell;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.rascalmpl.debug.IRascalMonitor;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.NullRascalMonitor;
import org.rascalmpl.interpreter.staticErrors.CommandlineError;
import org.rascalmpl.interpreter.utils.RascalManifest;
import org.rascalmpl.uri.URIUtil;

import io.usethesource.vallang.IInteger;
import io.usethesource.vallang.IValue;
import io.usethesource.vallang.io.StandardTextWriter;

public class ManifestRunner implements ShellRunner {
    private final RascalManifest mf;
    private final Evaluator eval;


    public ManifestRunner(RascalManifest mf, InputStream input, OutputStream stdout, OutputStream stderr) {
        assert mf.hasManifest(ManifestRunner.class);
        this.mf = mf;
        this.eval = ShellEvaluatorFactory.getDefaultEvaluator(input, stdout, stderr);
        addExtraSourceFolders();
    } 

    void addExtraSourceFolders() {
        for (String root : mf.getSourceRoots(ManifestRunner.class)) {
            eval.addRascalSearchPath(URIUtil.getChildLocation(URIUtil.rootLocation("manifest"), root)); 
        }
    }

    @Override
    public void run(String[] args) throws IOException {
        IRascalMonitor monitor = new NullRascalMonitor();
        String module = mf.getMainModule(ManifestRunner.class);
        assert module != null;
        eval.doImport(monitor, module);

        String main = mf.getMainFunction(ManifestRunner.class);
        main = main != null ? main : RascalManifest.DEFAULT_MAIN_FUNCTION;

        try {
            IValue v = eval.main(monitor, module, main, args);

            if (v != null && v.getType().isInteger()) {
                System.exit(((IInteger) v).intValue());
            } else {
                if (v != null) {
                    new StandardTextWriter(true).write(v, eval.getOutPrinter());
                }
                eval.getOutPrinter().flush();
                System.exit(0);
            }
        } catch (CommandlineError e) {
            System.err.println(e.getMessage());
            System.err.println(e.help("java -jar ..."));
        }
    }
}
