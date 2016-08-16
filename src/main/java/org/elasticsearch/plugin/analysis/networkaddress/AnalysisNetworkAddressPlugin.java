package org.elasticsearch.plugin.analysis.networkaddress;

import java.util.Collection;
import java.util.Collections;

import org.elasticsearch.common.inject.Module;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.index.analysis.NetworkAddressAnalysisBinderProcessor;
import org.elasticsearch.indices.analysis.IndicesAnalysisModule;
import org.elasticsearch.plugins.Plugin;

public class AnalysisNetworkAddressPlugin extends Plugin {

    @Override
    public String name() {
        return "elasticsearch-network-analysis";
    }

    @Override
    public String description() {
        return "";
    }
    
    @Override
    public Collection<Module> nodeModules() {
        return Collections.<Module>singletonList(new IndicesAnalysisModule());
    }

    public void onModule(AnalysisModule module) {
        module.addProcessor(new NetworkAddressAnalysisBinderProcessor());
    }
}
