/*
 * Copyright 2010-2018 Boxfuse GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flywaydb.core.internal.database.saphana;

import org.flywaydb.core.api.configuration.Configuration;
import org.flywaydb.core.internal.database.base.Database;
import org.flywaydb.core.internal.placeholder.PlaceholderReplacer;
import org.flywaydb.core.internal.resource.ResourceProvider;
import org.flywaydb.core.internal.sqlscript.SqlStatementBuilderFactory;

import java.sql.Connection;

/**
 * SAP HANA database.
 */
public class SAPHANADatabase extends Database<SAPHANAConnection> {
    /**
     * Creates a new instance.
     *
     * @param connection The connection to use.
     */
    public SAPHANADatabase(Configuration configuration, Connection connection, boolean originalAutoCommit



    ) {
        super(configuration, connection, originalAutoCommit



        );
    }

    @Override
    protected SAPHANAConnection getConnection(Connection connection



    ) {
        return new SAPHANAConnection(configuration, this, connection, originalAutoCommit



        );
    }

    @Override
    public void ensureSupported() {
        String version = majorVersion + "." + minorVersion;


        if (majorVersion == 1) {
            throw new org.flywaydb.core.internal.exception.FlywayEnterpriseUpgradeRequiredException("SAP", "HANA", version);
        }

        if (majorVersion > 2) {
            recommendFlywayUpgrade("SAP HANA", version);
        }

    }

    @Override
    protected SqlStatementBuilderFactory createSqlStatementBuilderFactory(PlaceholderReplacer placeholderReplacer



    ) {
        return new SAPHANAStatementBuilderFactory(placeholderReplacer);
    }

    @Override
    public String getDbName() {
        return "saphana";
    }

    @Override
    public boolean supportsDdlTransactions() {
        return false;
    }

    @Override
    public boolean supportsChangingCurrentSchema() {
        return true;
    }

    @Override
    public String getBooleanTrue() {
        return "1";
    }

    @Override
    public String getBooleanFalse() {
        return "0";
    }

    @Override
    public String doQuote(String identifier) {
        return "\"" + identifier + "\"";
    }

    @Override
    public boolean catalogIsSchema() {
        return false;
    }

}