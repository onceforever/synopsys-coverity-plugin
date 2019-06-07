/**
 * synopsys-coverity
 *
 * Copyright (c) 2019 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.jenkins.coverity.extensions.buildstep;

import java.io.Serializable;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import com.synopsys.integration.jenkins.SerializationHelper;
import com.synopsys.integration.jenkins.coverity.extensions.CoverityAnalysisType;
import com.synopsys.integration.jenkins.coverity.extensions.CoverityCaptureType;
import com.synopsys.integration.jenkins.coverity.extensions.utils.CommonFieldValueProvider;

import hudson.Extension;
import hudson.util.ListBoxModel;

public class SimpleCoverityRunConfiguration extends CoverityRunConfiguration implements Serializable {
    private static final long serialVersionUID = -4515622706956940126L;

    static {
        // TODO: Migrated in 2.1.0 -- Remove migration in 3.0.0
        SerializationHelper.migrateFieldFrom("buildCommand", SimpleCoverityRunConfiguration.class, "sourceArgument");
    }

    private final CoverityCaptureType coverityCaptureType;
    private final CoverityAnalysisType coverityAnalysisType;
    private final CommandArguments commandArguments;
    private String sourceArgument;

    @DataBoundConstructor
    public SimpleCoverityRunConfiguration(final CoverityCaptureType coverityCaptureType, final CoverityAnalysisType coverityAnalysisType, final CommandArguments commandArguments) {
        this.coverityCaptureType = coverityCaptureType;
        this.coverityAnalysisType = coverityAnalysisType;
        this.commandArguments = commandArguments;
    }

    public CoverityCaptureType getCoverityCaptureType() {
        return coverityCaptureType;
    }

    public String getSourceArgument() {
        return sourceArgument;
    }

    @DataBoundSetter
    public void setSourceArgument(final String sourceArgument) {
        this.sourceArgument = sourceArgument;
    }

    public CoverityAnalysisType getCoverityAnalysisType() {
        return coverityAnalysisType;
    }

    public CommandArguments getCommandArguments() {
        return commandArguments;
    }

    public Optional<CommandArguments> getCommandArgumentsSafely() {
        return Optional.ofNullable(commandArguments);
    }

    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    @Override
    public RunConfigurationType getRunConFigurationType() {
        return RunConfigurationType.SIMPLE;
    }

    @Extension
    public static class DescriptorImpl extends CoverityRunConfiguration.DescriptorImpl {
        public DescriptorImpl() {
            super(SimpleCoverityRunConfiguration.class);
            load();
        }

        public ListBoxModel doFillCoverityCaptureTypeItems() {
            return CommonFieldValueProvider.getListBoxModelOf(CoverityCaptureType.values());
        }

        public ListBoxModel doFillCoverityAnalysisTypeItems() {
            return CommonFieldValueProvider.getListBoxModelOf(CoverityAnalysisType.values());
        }

        @Override
        @Nonnull
        public String getDisplayName() {
            return RunConfigurationType.SIMPLE.getDisplayName();
        }
    }

}
