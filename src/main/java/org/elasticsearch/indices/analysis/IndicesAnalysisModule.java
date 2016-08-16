package org.elasticsearch.indices.analysis;

import org.elasticsearch.common.inject.AbstractModule;

public class IndicesAnalysisModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IndicesAnalysis.class).asEagerSingleton();
    }
}
