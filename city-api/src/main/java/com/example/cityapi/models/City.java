package com.example.cityapi.models;

import lombok.*;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "CITY")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SHORT_TITLE")
    private String short_title;

    @Column(name = "AGENCY_NAME")
    private String agency_name;

    @Column(name = "REQUEST_ID")
    private String request_id;

    @Column(name = "SECTION_NAME")
    private String section_name;

    public Record(String short_title, String agency_name, String request_id, String section_name) {
        this.short_title = short_title;
        this.agency_name = agency_name;
        this.request_id = request_id;
        this.section_name = section_name;
    }
}