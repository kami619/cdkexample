package com.myorg;

import software.amazon.awscdk.services.ec2.SubnetSelection;
import software.amazon.awscdk.services.ec2.SubnetType;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.rds.*;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

public class CdkWorkshopStack extends Stack {
    public CdkWorkshopStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public CdkWorkshopStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        Vpc vpc = Vpc.Builder.create(this, "MyVpc")
                .maxAzs(2) // Adjust based on your needs
                .build();

        DatabaseCluster auroraCluster = DatabaseCluster.Builder.create(this, "MyAuroraCluster")
                .engine(DatabaseClusterEngine.auroraPostgres(
                        AuroraPostgresClusterEngineProps.builder()
                                .version(AuroraPostgresEngineVersion.VER_15_5) // Specify your version
                                .build()))
                .instanceProps(InstanceProps.builder()
                        .vpc(vpc)
                        .vpcSubnets(SubnetSelection.builder()
                                .subnetType(SubnetType.PRIVATE_WITH_EGRESS)
                                .build())
                        .build())
                .instances(2) // For multi-AZ
                .build();
    }
}