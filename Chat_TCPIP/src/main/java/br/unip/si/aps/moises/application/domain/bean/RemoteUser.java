package br.unip.si.aps.moises.application.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RemoteUser implements User{
	private String name;
	private String id;
}
