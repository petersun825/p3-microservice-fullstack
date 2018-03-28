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
@Table(name = "RECORDS")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "AGENCY")
    private String agency;

    @Column(name = "REQUEST_ID")
    private String request_id;

    @Column(name = "START")
    private String start;

    public Record(String title, String agency, String requestId, String start) {
        this.title = title;
        this.agency = agency;
        this.request_id = requestId;
        this.start = start;
    }
}