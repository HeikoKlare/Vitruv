package tools.vitruv.applications.pcmjava.tests.pojotransformations.gplimplementation.java2pcm

import tools.vitruv.framework.change.processing.impl.AbstractChange2CommandTransformingProviding
import tools.vitruv.framework.change.processing.Change2CommandTransformingProviding
import tools.vitruv.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.Java2PCMChange2CommandTransformer

package class Change2CommandTransformingProvidingFactory {
	static def Change2CommandTransformingProviding createJava2PcmGplImplementationTransformingProviding() {
		return AbstractChange2CommandTransformingProviding.createChange2CommandTransformingProviding(
			#[new Java2PCMChange2CommandTransformer()]
		)
	}
}