package tools.vitruv.framework.change.echange.feature.list.util

import org.eclipse.emf.common.command.Command
import tools.vitruv.framework.change.echange.feature.list.util.ListSwitch
import tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange
import tools.vitruv.framework.change.echange.feature.attribute.util.AttributeApplyCommandSwitch
import tools.vitruv.framework.change.echange.feature.reference.util.ReferenceApplyCommandSwitch

public class ListApplyCommandSwitch extends ListSwitch<Command> {
	def public Command caseRemoveFromListEChange(RemoveFromListEChange object) {
		var result = (new AttributeApplyCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new ReferenceApplyCommandSwitch()).doSwitch(object)
		}
		return result
	}
}

